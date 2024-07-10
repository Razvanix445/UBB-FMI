#pragma once
#include "service.h"
#include "CosCRUDGUI.h"
#include "CosReadOnlyGUI.h"
#include <QApplication>
#include <QTextEdit>
#include <QtWidgets/QApplication>
#include <QtWidgets/qlabel.h>
#include <QtWidgets/qwidget.h>
#include <QtWidgets/qpushbutton.h>
#include <QtWidgets/qboxlayout.h>
#include <QtWidgets/qlineedit.h>
#include <QtWidgets/qformlayout.h>
#include <QtWidgets/qlistwidget.h>
#include <QFormLayout>
#include <qdebug.h>
#include <qmessagebox.h>
#include <qmainwindow.h>
#include <qmenubar.h>
#include <qcombobox.h>
#include <qheaderview.h>
#include <qtablewidget.h>
#include <qfont.h>
#include <qscrollarea.h>
#include <qsplitter.h>
#include <vector>
#include <string>

using std::vector;
using std::string;

class FilmGUI : public QWidget {
public:
    FilmGUI(Service& service, CosInchirieriFilme& cos) : service{ service }, cos{ cos } {
        initGUI();
        loadData();
        initConnect();
    }
private:
    Service& service;
    CosInchirieriFilme& cos;
    vector<Film> filme;

    //CosCRUDGUI* cosCRUDGUI = new CosCRUDGUI();
    //CosReadOnlyGUI* cosReadOnlyGUI = new CosReadOnlyGUI();

    //QListWidget* lista = new QListWidget{};
    QScrollArea* scrollArea = new QScrollArea();
    QTableWidget* tabelFilme = new QTableWidget();
    QListWidget* listaInchirieri = new QListWidget{};

    QLineEdit* titluEdit = new QLineEdit;
    QLineEdit* genEdit = new QLineEdit;
    QLineEdit* anAparitieEdit = new QLineEdit;
    QLineEdit* actorPrincipalEdit = new QLineEdit;
    QLineEdit* numarAdaugareRandomInCosEdit = new QLineEdit;
    QLineEdit* numeFisierCSV = new QLineEdit;
    QLineEdit* numeFisierHTML = new QLineEdit;

    QPushButton* butonStergereTitlu = new QPushButton{ "&Șterge" };
    QPushButton* butonStergereGen = new QPushButton{ "&Șterge" };
    QPushButton* butonStergereAnAparitie = new QPushButton{ "&Șterge" };
    QPushButton* butonStergereActorPrincipal = new QPushButton{ "&Șterge" };
    QPushButton* butonStergere = new QPushButton{ "&Șterge toate câmpurile" };

    QPushButton* butonExit = new QPushButton{ "&Exit" };
    QPushButton* butonAdauga = new QPushButton{ "&Adaugă" };
    QPushButton* butonSterge = new QPushButton{ "&Șterge" };
    QPushButton* butonModifica = new QPushButton{ "&Modifică" };
    QPushButton* butonCauta = new QPushButton{ "&Caută" };
    QPushButton* butonFiltreaza = new QPushButton{ "&Filtrează" };
    QPushButton* butonSorteaza = new QPushButton{ "&Sortează" };
    QPushButton* butonUndo = new QPushButton{ "&Undo" };

    QPushButton* butonAdaugareInCos = new QPushButton{ "&Adaugă în coș" };
    QPushButton* butonStergereDinCos = new QPushButton{ "&Șterge din coș" };
    QPushButton* butonGolireCos = new QPushButton{ "&Golește cosul" };
    QPushButton* butonAdaugareRandomInCos = new QPushButton{ "&Adaugă random în cos" };

    QPushButton* butonExportInCSV = new QPushButton{ "&Exportă coșul în CSV" };
    QPushButton* butonExportInHTML = new QPushButton{ "&Exportă coșul în HTML" };

    QPushButton* butonCosCRUDGUI = new QPushButton{ "&CosCRUDGUI" };
    QPushButton* butonCosReadOnlyGUI = new QPushButton{ "&CosReadOnlyGUI" };

    QLabel* numarFilmeLabel = new QLabel("Număr de filme: ");
    QString numarFilme = QString::number(service.getAll().size());

    void initConnect() {
        QObject::connect(butonExit, &QPushButton::clicked, []() {
            qApp->quit();
            });

        QObject::connect(butonAdauga, &QPushButton::clicked, [&]() {
            auto titluFilm = titluEdit->text().toStdString();
            auto genFilm = genEdit->text().toStdString();
            auto anAparitie = anAparitieEdit->text().toInt();
            auto actorPrincipal = actorPrincipalEdit->text().toStdString();

            if (titluFilm.empty() || genFilm.empty() || !anAparitie || actorPrincipal.empty()) {
                QMessageBox::information(this, "Ceva nu a mers bine!", "Ceva nu a mers bine!");
                return;
            }

            try {
                service.adaugaFilm(titluFilm, genFilm, anAparitie, actorPrincipal);
            }
            catch (ExceptieRepo& exceptie) {
                QMessageBox::warning(this, "Atenție!", QString::fromStdString(exceptie.getMesaj()));
            }
            catch (ExceptieValidare& exceptie) {
                QMessageBox::warning(this, "Atenție!", QString::fromStdString(exceptie.getMesaj()[0]));
            }

            loadData();
            });

        QObject::connect(butonSterge, &QPushButton::clicked, [&]() {
            auto titluFilm = titluEdit->text().toStdString();

            if (titluFilm.empty()) {
                QMessageBox::information(this, "Ceva nu a mers bine!", "Ceva nu a mers bine!");
                return;
            }

            try {
                service.stergeFilm(titluFilm);
            }
            catch (ExceptieRepo& exceptie) {
                QMessageBox::warning(this, "Atenție!", QString::fromStdString(exceptie.getMesaj()));
            }
            catch (ExceptieValidare& exceptie) {
                QMessageBox::warning(this, "Atenție!", QString::fromStdString(exceptie.getMesaj()[0]));
            }

            loadData();
            });

        QObject::connect(butonModifica, &QPushButton::clicked, [&]() {
            auto titluFilm = titluEdit->text().toStdString();
            auto genFilm = genEdit->text().toStdString();
            auto anAparitie = anAparitieEdit->text().toInt();
            auto actorPrincipal = actorPrincipalEdit->text().toStdString();

            if (titluFilm.empty() || genFilm.empty() || !anAparitie || actorPrincipal.empty()) {
                QMessageBox::information(this, "Ceva nu a mers bine!", "Ceva nu a mers bine!");
                return;
            }

            try {
                service.modificaFilm(titluFilm, genFilm, anAparitie, actorPrincipal);
            }
            catch (ExceptieRepo& exceptie) {
                QMessageBox::warning(this, "Atenție!", QString::fromStdString(exceptie.getMesaj()));
            }
            catch (ExceptieValidare& exceptie) {
                QMessageBox::warning(this, "Atenție!", QString::fromStdString(exceptie.getMesaj()[0]));
            }

            loadData();
            });

        QObject::connect(butonCauta, &QPushButton::clicked, [&]() {
            QDialog* dialogCautare = new QDialog(this);
            dialogCautare->resize(600, 400);
            QVBoxLayout* layoutDialog = new QVBoxLayout(dialogCautare);
            QPushButton* butonCautareDialog = new QPushButton{ "&Cauta" };

            QLineEdit* lineEditTitlu = new QLineEdit(dialogCautare);
            QListWidget* listaFilme = new QListWidget(dialogCautare);

            layoutDialog->addWidget(new QLabel("Titlu:", dialogCautare));
            layoutDialog->addWidget(lineEditTitlu);
            layoutDialog->addWidget(listaFilme);
            layoutDialog->addWidget(butonCautareDialog);

            QObject::connect(butonCautareDialog, &QPushButton::clicked, [=]() {
                if (lineEditTitlu->text() != "") {
                    try {
                        auto film = service.cautaFilm(lineEditTitlu->text().toStdString());
                        listaFilme->clear();
                        QString item = QString::fromStdString(film.getTitluFilm() + ", " + film.getGenFilm() + ", " +
                            std::to_string(film.getAnAparitie()) + ", " + film.getActorPrincipal());
                        listaFilme->addItem(item);
                    }
                    catch (ExceptieRepo& exceptie) {
                        QMessageBox::warning(this, "Atenție!", QString::fromStdString(exceptie.getMesaj()));
                    }
                    catch (ExceptieValidare& exceptie) {
                        QMessageBox::warning(this, "Atenție!", QString::fromStdString(exceptie.getMesaj()[0]));
                    }
                }
                });
            dialogCautare->show();
            });

        QObject::connect(butonFiltreaza, &QPushButton::clicked, [&]() {
            QDialog* dialogFiltrare = new QDialog(this);
            dialogFiltrare->resize(600, 600);
            QVBoxLayout* layoutDialog = new QVBoxLayout(dialogFiltrare);
            QPushButton* butonFiltrareDialog = new QPushButton{ "&Filtrează" };

            QLineEdit* lineEditGen = new QLineEdit(dialogFiltrare);
            QLineEdit* lineEditAn = new QLineEdit(dialogFiltrare);
            QListWidget* listaFilme = new QListWidget(dialogFiltrare);

            layoutDialog->addWidget(new QLabel("Gen:", dialogFiltrare));
            layoutDialog->addWidget(lineEditGen);
            layoutDialog->addWidget(new QLabel("An:", dialogFiltrare));
            layoutDialog->addWidget(lineEditAn);
            layoutDialog->addWidget(listaFilme);
            layoutDialog->addWidget(butonFiltrareDialog);

            QObject::connect(butonFiltrareDialog, &QPushButton::clicked, [=]() {
                if (lineEditGen->text() != "" && lineEditAn->text() == "") {
                    auto filme = service.filtreazaDupaGen(lineEditGen->text().toStdString());
                    listaFilme->clear();
                    for (const auto& film : filme) {
                        QString item = QString::fromStdString(film.getTitluFilm() + ", " + film.getGenFilm() + ", " +
                            std::to_string(film.getAnAparitie()) + ", " + film.getActorPrincipal());
                        listaFilme->addItem(item);
                    }
                }
                if (lineEditGen->text() == "" && lineEditAn->text() != "") {
                    auto filme = service.filtreazaDupaAnAparitie(lineEditAn->text().toInt());
                    listaFilme->clear();
                    for (const auto& film : filme) {
                        QString item = QString::fromStdString(film.getTitluFilm() + ", " + film.getGenFilm() + ", " +
                            std::to_string(film.getAnAparitie()) + ", " + film.getActorPrincipal());
                        listaFilme->addItem(item);
                    }
                }
                });
            dialogFiltrare->show();
            });

        QObject::connect(butonSorteaza, &QPushButton::clicked, [&]() {
            QDialog* dialogSortare = new QDialog(this);
            dialogSortare->resize(600, 600);
            QVBoxLayout* layoutDialog = new QVBoxLayout(dialogSortare);
            QPushButton* butonSortareDialog = new QPushButton{ "&Sorteaza" };

            QLineEdit* lineEditReperSortare = new QLineEdit(dialogSortare);
            QLineEdit* lineEditOrdineSortare = new QLineEdit(dialogSortare);
            QListWidget* listaFilme = new QListWidget(dialogSortare);

            layoutDialog->addWidget(new QLabel("Sortare după (1 -> titlu / 2 -> actor / 3 -> an+gen):", dialogSortare));
            layoutDialog->addWidget(lineEditReperSortare);
            layoutDialog->addWidget(new QLabel("Ordine sortare (1 -> crescător / 2 -> descrescător):", dialogSortare));
            layoutDialog->addWidget(lineEditOrdineSortare);
            layoutDialog->addWidget(listaFilme);
            layoutDialog->addWidget(butonSortareDialog);

            QObject::connect(butonSortareDialog, &QPushButton::clicked, [=]() {
                if (lineEditReperSortare->text() != "" && lineEditOrdineSortare->text() != "") {
                    auto filme = service.sorteaza(lineEditReperSortare->text().toInt(), lineEditOrdineSortare->text().toInt());
                    listaFilme->clear();
                    for (const auto& film : filme) {
                        QString item = QString::fromStdString(film.getTitluFilm() + ", " + film.getGenFilm() + ", " +
                            std::to_string(film.getAnAparitie()) + ", " + film.getActorPrincipal());
                        listaFilme->addItem(item);
                    }
                }
                });
            dialogSortare->show();
            });

        QObject::connect(butonUndo, &QPushButton::clicked, [&]() {
            try {
                service.undo();
            }
            catch (ExceptieRepo& exceptie) {
                QMessageBox::warning(this, "Atenție!", QString::fromStdString(exceptie.getMesaj()));
            }
            catch (ExceptieValidare& exceptie) {
                QMessageBox::warning(this, "Atenție!", QString::fromStdString(exceptie.getMesaj()[0]));
            }
            loadData();
            });
        /*
        QObject::connect(lista, &QListWidget::itemClicked, [&](QListWidgetItem* item) {
            QString itemText = item->text();
            QStringList parti = itemText.split(", ");
            QString titluFilm = parti[0];
            QString genFilm = parti[1];
            int anAparitie = parti[2].toInt();
            QString actorPrincipal = parti[3];

            titluEdit->setText(titluFilm);
            genEdit->setText(genFilm);
            anAparitieEdit->setText(QString::fromStdString(std::to_string(anAparitie)));
            actorPrincipalEdit->setText(actorPrincipal);
        });
        */
        QObject::connect(tabelFilme, &QTableWidget::itemClicked, [&](QTableWidgetItem* item) {
            QString titluFilm = tabelFilme->item(item->row(), 0)->text();
            QString genFilm = tabelFilme->item(item->row(), 1)->text();
            int anAparitie = tabelFilme->item(item->row(), 2)->text().toInt();
            QString actorPrincipal = tabelFilme->item(item->row(), 3)->text();

            titluEdit->setText(titluFilm);
            genEdit->setText(genFilm);
            anAparitieEdit->setText(QString::number(anAparitie));
            actorPrincipalEdit->setText(actorPrincipal);
            });

        QObject::connect(listaInchirieri, &QListWidget::itemClicked, [&](QListWidgetItem* item) {
            QString itemText = item->text();
            QStringList parti = itemText.split(", ");
            QString titluFilm = parti[0];
            QString genFilm = parti[1];
            int anAparitie = parti[2].toInt();
            QString actorPrincipal = parti[3];

            titluEdit->setText(titluFilm);
            genEdit->setText(genFilm);
            anAparitieEdit->setText(QString::fromStdString(std::to_string(anAparitie)));
            actorPrincipalEdit->setText(actorPrincipal);
            });

        QObject::connect(butonStergereTitlu, &QPushButton::clicked, [&]() {
            titluEdit->setText("");
            });

        QObject::connect(butonStergereGen, &QPushButton::clicked, [&]() {
            genEdit->setText("");
            });

        QObject::connect(butonStergereAnAparitie, &QPushButton::clicked, [&]() {
            anAparitieEdit->setText("");
            });

        QObject::connect(butonStergereActorPrincipal, &QPushButton::clicked, [&]() {
            actorPrincipalEdit->setText("");
            });

        QObject::connect(butonStergere, &QPushButton::clicked, [&]() {
            titluEdit->setText("");
            genEdit->setText("");
            anAparitieEdit->setText("");
            actorPrincipalEdit->setText("");
            });

        QObject::connect(butonAdaugareInCos, &QPushButton::clicked, [&]() {
            auto titluFilm = titluEdit->text().toStdString();

            if (titluFilm.empty()) {
                QMessageBox::information(this, "Ceva nu a mers bine!", "Ceva nu a mers bine!");
                return;
            }

            try {
                auto iterator = service.cautaFilm(titluFilm);
            }
            catch (ExceptieRepo& exceptie) {
                QMessageBox::warning(this, "Atenție!", "Filmul nu este disponibil!");
                return;
            }

            for (const auto& film : service.getAllCosInchirieri()) {
                if (titluFilm == film.getTitluFilm()) {
                    QMessageBox::warning(this, "Atenție!", "Filmul este deja in cos!");
                    break;
                }
            }

            try {
                service.adaugaFilmInCosInchirieri(titluFilm);
            }
            catch (ExceptieCosFilme& exceptie) {
                QMessageBox::warning(this, "Atenție!", QString::fromStdString(exceptie.getMesaj()));
                return;
            }
            updateListaInchirieri();
            });

        QObject::connect(butonStergereDinCos, &QPushButton::clicked, [&]() {
            auto titluFilm = titluEdit->text().toStdString();

            if (titluFilm.empty()) {
                QMessageBox::information(this, "Ceva nu a mers bine!", "Ceva nu a mers bine!");
                return;
            }

            try {
                service.stergeFilmDinCosInchirieri(titluFilm);
            }
            catch (ExceptieCosFilme& exceptie) {
                QMessageBox::warning(this, "Atenție!", QString::fromStdString(exceptie.getMesaj()));
            }
            updateListaInchirieri();
            });

        QObject::connect(butonGolireCos, &QPushButton::clicked, [&]() {
            service.golesteCosInchirieri();
            updateListaInchirieri();
            });

        QObject::connect(butonAdaugareRandomInCos, &QPushButton::clicked, [&]() {
            if (numarAdaugareRandomInCosEdit->text().toStdString() == "")
                QMessageBox::information(this, "Atenție!", "Introduceți un număr de filme pentru închiriat!");

            int numarDeFilmeIntroduse = service.getAll().size();
            if (numarAdaugareRandomInCosEdit->text().toInt() <= numarDeFilmeIntroduse)
                service.umpleRandomCosInchirieri(numarAdaugareRandomInCosEdit->text().toInt());
            else
                QMessageBox::warning(this, "Atenție!", "Nu există suficiente filme introduse!");
            updateListaInchirieri();
            });

        QObject::connect(butonExportInCSV, &QPushButton::clicked, [&]() {
            if (numeFisierCSV->text().toStdString() == "") {
                QMessageBox::information(this, "Atenție!", "Introduceți un nume de fișier cu extensia .csv!");
                return;
            }

            int numarDeFilmeIntroduse = service.getAll().size();
            try {
                service.exportaInCSV(numeFisierCSV->text().toStdString(), service.getAllCosInchirieri());
            }
            catch (ExceptieRepo& exceptie) {
                QMessageBox::warning(this, "Atenție!", QString::fromStdString(exceptie.getMesaj()));
            }
            updateListaInchirieri();
            });

        QObject::connect(butonExportInHTML, &QPushButton::clicked, [&]() {
            if (numeFisierHTML->text().toStdString() == "") {
                QMessageBox::information(this, "Atenție!", "Introduceți un nume de fișier cu extensia .html!");
                return;
            }

            int numarDeFilmeIntroduse = service.getAll().size();
            try {
                service.exportaInHTML(numeFisierHTML->text().toStdString(), service.getAllCosInchirieri());
            }
            catch (ExceptieRepo& exceptie) {
                QMessageBox::warning(this, "Atenție!", QString::fromStdString(exceptie.getMesaj()));
            }
            updateListaInchirieri();
            });

        QObject::connect(butonCosCRUDGUI, &QPushButton::clicked, [&]() {
            CosCRUDGUI* cosCRUDGUI = new CosCRUDGUI(service, cos);
            cosCRUDGUI->setAttribute(Qt::WA_DeleteOnClose);
            cosCRUDGUI->show();
            });

        QObject::connect(butonCosReadOnlyGUI, &QPushButton::clicked, [&]() {
            CosReadOnlyGUI* cosReadOnlyGUI = new CosReadOnlyGUI(cos);
            cosReadOnlyGUI->setAttribute(Qt::WA_DeleteOnClose);
            cosReadOnlyGUI->show();
            });
    }

    void updateListaInchirieri() {
        listaInchirieri->clear();
        for (const auto& film : service.getAllCosInchirieri()) {
            QString item = QString::fromStdString(film.getTitluFilm() + ", " + film.getGenFilm() + ", " +
                std::to_string(film.getAnAparitie()) + ", " + film.getActorPrincipal());
            listaInchirieri->addItem(item);
        }
    }

    void loadData() {
        tabelFilme->clearContents();
        tabelFilme->setRowCount(0);

        auto filme = service.getAll();
        for (const auto& film : filme) {
            int row = tabelFilme->rowCount();
            tabelFilme->insertRow(row);

            QTableWidgetItem* titluItem = new QTableWidgetItem(QString::fromStdString(film.getTitluFilm()));
            QTableWidgetItem* genItem = new QTableWidgetItem(QString::fromStdString(film.getGenFilm()));
            QTableWidgetItem* anAparitieItem = new QTableWidgetItem(QString::number(film.getAnAparitie()));
            QTableWidgetItem* actorPrincipalItem = new QTableWidgetItem(QString::fromStdString(film.getActorPrincipal()));

            tabelFilme->setItem(row, 0, titluItem);
            tabelFilme->setItem(row, 1, genItem);
            tabelFilme->setItem(row, 2, anAparitieItem);
            tabelFilme->setItem(row, 3, actorPrincipalItem);
        }
        for (int column = 0; column < tabelFilme->columnCount() - 1; ++column) {
            tabelFilme->horizontalHeader()->setSectionResizeMode(column, QHeaderView::ResizeToContents);
        }
        tabelFilme->horizontalHeader()->setSectionResizeMode(tabelFilme->columnCount() - 1, QHeaderView::Stretch);

        int numarFilme = service.getAll().size();
        numarFilmeLabel->setText("Număr de filme: " + QString::number(numarFilme));

        /*
        lista->clear();
        auto filme = service.getAll();
        for (const auto& film : filme) {
            QString item = QString::fromStdString(film.getTitluFilm() + ", " + film.getGenFilm() + ", " +
                std::to_string(film.getAnAparitie()) + ", " + film.getActorPrincipal());
            lista->addItem(item);
        }
        int numarFilme = service.getAll().size();
        numarFilmeLabel->setText("Număr de filme: " + QString::number(numarFilme));
        */
    }

    void initGUI() {
        QHBoxLayout* layoutPrincipal = new QHBoxLayout{};
        auto layoutDreapta = new QVBoxLayout;
        auto layoutStanga = new QVBoxLayout;
        auto layoutDreaptaSus = new QVBoxLayout;
        auto layoutDreaptaCentru = new QVBoxLayout;
        auto layoutDreaptaCentruJos = new QVBoxLayout;
        auto layoutDreaptaJos = new QVBoxLayout;

        setLayout(layoutPrincipal);

        //Layout Stanga
        auto layoutLista = new QHBoxLayout{};

        tabelFilme->setColumnCount(4);
        tabelFilme->setHorizontalHeaderLabels(QStringList() << "Titlu" << "Gen" << "An Aparitie" << "Actor Principal");

        numarFilmeLabel->setText("Număr de filme: " + numarFilme);
        layoutStanga->addWidget(tabelFilme, 1);
        layoutStanga->addWidget(numarFilmeLabel);

        //Layout Dreapta Sus
        auto formLayout = new QFormLayout;
        formLayout->addRow("Titlu", titluEdit);
        formLayout->addRow("Gen", genEdit);
        formLayout->addRow("An Apariție", anAparitieEdit);
        formLayout->addRow("Actor Principal", actorPrincipalEdit);

        QFont fontText = tabelFilme->font();
        fontText.setPointSize(12);
        titluEdit->setFont(fontText), genEdit->setFont(fontText), anAparitieEdit->setFont(fontText), actorPrincipalEdit->setFont(fontText);

        auto butoaneStergereLayout = new QVBoxLayout;
        butoaneStergereLayout->addWidget(butonStergereTitlu);
        butoaneStergereLayout->addWidget(butonStergereGen);
        butoaneStergereLayout->addWidget(butonStergereAnAparitie);
        butoaneStergereLayout->addWidget(butonStergereActorPrincipal);
        butoaneStergereLayout->addWidget(butonStergere);

        QFont fontButoaneStergere = tabelFilme->font();
        fontButoaneStergere.setPointSize(10);
        butonStergereTitlu->setFont(fontButoaneStergere), butonStergereGen->setFont(fontButoaneStergere), butonStergereAnAparitie->setFont(fontButoaneStergere), butonStergereActorPrincipal->setFont(fontButoaneStergere);

        auto layoutButoaneCerintaLab = new QHBoxLayout{};

        //Layout Dreapta Centru
        auto layoutButoaneCosInchirieri = new QHBoxLayout{};
        auto casetaAdaugareRandom = new QFormLayout;

        QLabel* textCosInchirieri = new QLabel("Coș de închirieri");
        layoutDreaptaCentru->addWidget(textCosInchirieri);
        QFont fontTextCosInchirieri = tabelFilme->font();
        fontTextCosInchirieri.setPointSize(18);
        textCosInchirieri->setFont(fontTextCosInchirieri);
        textCosInchirieri->setAlignment(Qt::AlignCenter);

        casetaAdaugareRandom->addRow(numarAdaugareRandomInCosEdit);
        numarAdaugareRandomInCosEdit->setPlaceholderText("Introduceți un număr de filme");
        numarAdaugareRandomInCosEdit->setFixedWidth(185);
        casetaAdaugareRandom->setContentsMargins(380, 0, 0, 0);

        layoutButoaneCosInchirieri->addWidget(butonAdaugareInCos);
        layoutButoaneCosInchirieri->addWidget(butonStergereDinCos);
        layoutButoaneCosInchirieri->addWidget(butonAdaugareRandomInCos);
        layoutButoaneCosInchirieri->addWidget(butonGolireCos);

        //Layout Exporturi
        auto layoutButoaneExporturi = new QHBoxLayout{};
        auto layoutCaseteTextFisiereExport = new QHBoxLayout{};
        auto casetaNumeFisierCSV = new QFormLayout;
        auto casetaNumeFisierHTML = new QFormLayout;

        casetaNumeFisierCSV->addRow(numeFisierCSV);
        casetaNumeFisierHTML->addRow(numeFisierHTML);
        numeFisierCSV->setPlaceholderText("Introduceți fișier.csv");
        numeFisierCSV->setFixedWidth(185);
        numeFisierHTML->setPlaceholderText("Introduceți fișier.html");
        numeFisierHTML->setFixedWidth(185);

        layoutButoaneExporturi->addWidget(butonExportInCSV);
        layoutButoaneExporturi->addWidget(butonExportInHTML);
        layoutCaseteTextFisiereExport->addLayout(casetaNumeFisierCSV);
        layoutCaseteTextFisiereExport->addLayout(casetaNumeFisierHTML);

        //Layout Ferestre Observer
        auto layoutButoaneObserver = new QHBoxLayout{};
        layoutButoaneObserver->addWidget(butonCosCRUDGUI);
        layoutButoaneObserver->addWidget(butonCosReadOnlyGUI);

        //Layout Dreapta Jos
        auto layoutButoaneAdaugareStergereModificareCautare = new QHBoxLayout{};
        auto layoutButoaneFiltrareSortare = new QHBoxLayout{};
        auto layoutButoaneUndoExit = new QHBoxLayout{};
        auto formLayoutCuButoane = new QHBoxLayout{};

        layoutButoaneAdaugareStergereModificareCautare->addWidget(butonAdauga);
        layoutButoaneAdaugareStergereModificareCautare->addWidget(butonSterge);
        layoutButoaneAdaugareStergereModificareCautare->addWidget(butonModifica);
        layoutButoaneAdaugareStergereModificareCautare->addWidget(butonCauta);
        layoutButoaneFiltrareSortare->addWidget(butonFiltreaza);
        layoutButoaneFiltrareSortare->addWidget(butonSorteaza);
        layoutButoaneUndoExit->addWidget(butonUndo);
        layoutButoaneUndoExit->addWidget(butonExit);

        QFont fontButoane = tabelFilme->font();
        fontButoane.setPointSize(10);
        butonAdauga->setFont(fontButoane), butonSterge->setFont(fontButoane), butonModifica->setFont(fontButoane), butonCauta->setFont(fontButoane);
        butonFiltreaza->setFont(fontButoane), butonSorteaza->setFont(fontButoane), butonUndo->setFont(fontButoane), butonExit->setFont(fontButoane);

        //Adaugam splitter (despartitor) intre layoutStanga si layoutDreapta
        QSplitter* splitter = new QSplitter(Qt::Horizontal, this);
        splitter->setOpaqueResize(false);
        layoutPrincipal->addWidget(splitter);
        QWidget* widgetStanga = new QWidget();
        widgetStanga->setLayout(layoutStanga);
        QWidget* widgetDreapta = new QWidget();
        widgetDreapta->setLayout(layoutDreapta);
        splitter->addWidget(widgetStanga);
        splitter->addWidget(widgetDreapta);
        splitter->setStretchFactor(0, 1);

        //Organizare Layouts
        formLayoutCuButoane->addLayout(formLayout);
        formLayoutCuButoane->addLayout(butoaneStergereLayout);
        layoutDreaptaSus->addLayout(formLayoutCuButoane);
        layoutDreaptaSus->addStretch();

        layoutDreaptaCentru->addWidget(listaInchirieri);
        layoutDreaptaCentru->addLayout(layoutButoaneCosInchirieri);
        layoutDreaptaCentru->addLayout(casetaAdaugareRandom);

        layoutDreaptaCentruJos->addLayout(layoutButoaneExporturi);
        layoutDreaptaCentruJos->addLayout(layoutCaseteTextFisiereExport);

        layoutDreaptaJos->addStretch();
        layoutDreaptaJos->addLayout(layoutButoaneAdaugareStergereModificareCautare);
        layoutDreaptaJos->addLayout(layoutButoaneFiltrareSortare);
        layoutDreaptaJos->addLayout(layoutButoaneUndoExit);

        layoutLista->addLayout(layoutStanga);
        layoutLista->addLayout(layoutDreapta);

        layoutDreapta->addLayout(layoutDreaptaSus);
        layoutDreapta->addLayout(layoutDreaptaCentru);
        layoutDreapta->addLayout(layoutDreaptaCentruJos);
        layoutDreapta->addLayout(layoutButoaneObserver);
        layoutDreapta->addLayout(layoutDreaptaJos);
        
        layoutPrincipal->addLayout(layoutLista);
    }
};