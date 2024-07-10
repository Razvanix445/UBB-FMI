#pragma once
#include "service.h"
#include "observator.h"
#include <QtWidgets/qlabel.h>
#include <QtWidgets/qwidget.h>
#include <QtWidgets/qpushbutton.h>
#include <QtWidgets/qboxlayout.h>
#include <QtWidgets/qlineedit.h>
#include <QtWidgets/qformlayout.h>
#include <QtWidgets/qlistwidget.h>
#include <QtWidgets/qmessagebox.h>
#include <qtablewidget.h>
#include <qdebug.h>
#include <QFormLayout>
#include <qfont.h>
#include <QTextEdit>
#include <vector>
#include <string>

class CosCRUDGUI : public QWidget, public Observator {
public:
    CosCRUDGUI(Service& service, CosInchirieriFilme& cos) : service { service }, cos { cos } {
        initGUI();
        loadData();
        initConnect();
    }

    void updateaza() override {
        loadData();
    }

private:
    Service& service;
    CosInchirieriFilme& cos;
    vector<Film> filme;

    QListWidget* listaInchirieri = new QListWidget{};
    QLineEdit* numarAdaugareRandomInCosEdit = new QLineEdit;
    QPushButton* butonGolireCos = new QPushButton{ "&Golește cosul" };
    QPushButton* butonAdaugareRandomInCos = new QPushButton{ "&Adaugă random în cos" };

    void initGUI() {
        auto layoutPrincipalCos = new QVBoxLayout{};
        auto layoutButoaneCosInchirieri = new QHBoxLayout{};
        auto casetaAdaugareRandom = new QFormLayout;

        QLabel* textCosInchirieri = new QLabel("Coș de închirieri");

        casetaAdaugareRandom->addRow(numarAdaugareRandomInCosEdit);
        numarAdaugareRandomInCosEdit->setPlaceholderText("Introduceți un număr de filme");
        numarAdaugareRandomInCosEdit->setFixedWidth(185);

        layoutButoaneCosInchirieri->addWidget(butonAdaugareRandomInCos);
        layoutButoaneCosInchirieri->addWidget(butonGolireCos);

        setLayout(layoutPrincipalCos);

        layoutPrincipalCos->addWidget(textCosInchirieri);
        layoutPrincipalCos->addWidget(listaInchirieri);
        layoutPrincipalCos->addLayout(layoutButoaneCosInchirieri);
        layoutPrincipalCos->addLayout(casetaAdaugareRandom);
    }

    void loadData() {
        listaInchirieri->clear();
        for (const auto& film : cos.getAllCosInchirieri()) {
            QString item = QString::fromStdString(film.getTitluFilm() + ", " + film.getGenFilm() + ", " +
                std::to_string(film.getAnAparitie()) + ", " + film.getActorPrincipal());
            listaInchirieri->addItem(item);
        }
        filme.clear();
    }

    void initConnect() {
        cos.adaugaObservator(this);
        QObject::connect(butonGolireCos, &QPushButton::clicked, [&]() {
            service.golesteCosInchirieri();
            loadData();
            });

        QObject::connect(butonAdaugareRandomInCos, &QPushButton::clicked, [&]() {
            filme = service.getAll();
            if (numarAdaugareRandomInCosEdit->text().toStdString() == "")
                QMessageBox::information(this, "Atenție!", "Introduceți un număr de filme pentru închiriat!");

            int numarDeFilmeIntroduse = filme.size();
            if (numarAdaugareRandomInCosEdit->text().toInt() <= numarDeFilmeIntroduse)
                cos.umpleRandomCosInchirieri(numarAdaugareRandomInCosEdit->text().toInt(), filme);
            else
                QMessageBox::warning(this, "Atenție!", "Nu există suficiente filme introduse!");
            loadData();
            });
    }

    ~CosCRUDGUI() {
        cos.stergeObservator(this);
    }
};