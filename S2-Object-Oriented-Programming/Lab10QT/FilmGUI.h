#pragma once
#include "service.h"
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
#include <vector>
#include <string>

using std::vector;
using std::string;

class FilmGUI : public QWidget {
public:
    FilmGUI(Service& service) : service{service} {
		initGUI();
        loadData();
        initConnect();
	}
private:
    Service& service;
    vector<Film> filme;

    QListWidget* lista = new QListWidget;

    QLineEdit* titluEdit = new QLineEdit;
    QLineEdit* genEdit = new QLineEdit;
    QLineEdit* anAparitieEdit = new QLineEdit;
    QLineEdit* actorPrincipalEdit = new QLineEdit;

    QPushButton* butonExit = new QPushButton{ "&Exit" };
    QPushButton* butonAdauga = new QPushButton{ "&Adauga" };
    QPushButton* butonSterge = new QPushButton{ "&Sterge" };
    QPushButton* butonModifica = new QPushButton{ "&Modifica" };
    QPushButton* butonCauta = new QPushButton{ "&Cauta" };
    QPushButton* butonFiltreaza = new QPushButton{ "&Filtreaza" };
    QPushButton* butonSorteaza = new QPushButton{ "&Sorteaza" };
    QPushButton* butonUndo = new QPushButton{ "&Undo" };

    QPushButton* butonActiune = new QPushButton{ "&Actiune" };
    QPushButton* butonHistoricDrama = new QPushButton{ "&Historic Drama" };
    QPushButton* butonScienceFiction = new QPushButton{ "&Science-Fiction" };
    QPushButton* butonAmericanActionDrama = new QPushButton{ "&American Action Drama" };

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
                QMessageBox::warning(this, "Atentie!", QString::fromStdString(exceptie.getMesaj()));
            }
            catch (ExceptieValidare& exceptie) {
                QMessageBox::warning(this, "Atentie!", QString::fromStdString(exceptie.getMesaj()[0]));
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
                QMessageBox::warning(this, "Atentie!", QString::fromStdString(exceptie.getMesaj()));
            }
            catch (ExceptieValidare& exceptie) {
                QMessageBox::warning(this, "Atentie!", QString::fromStdString(exceptie.getMesaj()[0]));
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
                QMessageBox::warning(this, "Atentie!", QString::fromStdString(exceptie.getMesaj()));
            }
            catch (ExceptieValidare& exceptie) {
                QMessageBox::warning(this, "Atentie!", QString::fromStdString(exceptie.getMesaj()[0]));
            }

            loadData();
        });

        QObject::connect(butonCauta, &QPushButton::clicked, [&]() {
            QDialog* dialogCautare = new QDialog(this);
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
                        QMessageBox::warning(this, "Atentie!", QString::fromStdString(exceptie.getMesaj()));
                    }
                    catch (ExceptieValidare& exceptie) {
                        QMessageBox::warning(this, "Atentie!", QString::fromStdString(exceptie.getMesaj()[0]));
                    }
                }
            });
            dialogCautare->show();
        });

        QObject::connect(butonFiltreaza, &QPushButton::clicked, [&]() {
            QDialog* dialogFiltrare = new QDialog(this);
            QVBoxLayout* layoutDialog = new QVBoxLayout(dialogFiltrare);
            QPushButton* butonFiltrareDialog = new QPushButton{ "&Filtreaza" };

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
            QVBoxLayout* layoutDialog = new QVBoxLayout(dialogSortare);
            QPushButton* butonSortareDialog = new QPushButton{ "&Sorteaza" };

            QLineEdit* lineEditReperSortare = new QLineEdit(dialogSortare);
            QLineEdit* lineEditOrdineSortare = new QLineEdit(dialogSortare);
            QListWidget* listaFilme = new QListWidget(dialogSortare);

            layoutDialog->addWidget(new QLabel("Sortare dupa (1 -> titlu / 2 -> actor / 3 -> gen+an):", dialogSortare));
            layoutDialog->addWidget(lineEditReperSortare);
            layoutDialog->addWidget(new QLabel("Ordine sortare (1 -> crescator / 2 -> descrescator):", dialogSortare));
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
                QMessageBox::warning(this, "Atentie!", QString::fromStdString(exceptie.getMesaj()));
            }
            catch (ExceptieValidare& exceptie) {
                QMessageBox::warning(this, "Atentie!", QString::fromStdString(exceptie.getMesaj()[0]));
            }
            loadData();
        });

        /*
        QObject::connect(butonActiune, &QPushButton::clicked, [&]() {
            auto filmeDupaGen = service.filtreazaDupaGen("Actiune");
            string numarFilme = std::to_string(filmeDupaGen.size());
            QMessageBox::warning(this, "Numar de filme", "Numarul de filme cu genul Actiune este: " + QString::fromStdString(numarFilme));
        });

        QObject::connect(butonHistoricDrama, &QPushButton::clicked, [&]() {
            auto filmeDupaGen = service.filtreazaDupaGen("Historic Drama");
            string numarFilme = std::to_string(filmeDupaGen.size());
            QMessageBox::warning(this, "Numar de filme", "Numarul de filme cu genul Historic Drama este: " + QString::fromStdString(numarFilme));
        });

        QObject::connect(butonScienceFiction, &QPushButton::clicked, [&]() {
            auto filmeDupaGen = service.filtreazaDupaGen("Science-Fiction");
            string numarFilme = std::to_string(filmeDupaGen.size());
            QMessageBox::warning(this, "Numar de filme", "Numarul de filme cu genul Science-Fiction este: " + QString::fromStdString(numarFilme));
        });

        QObject::connect(butonAmericanActionDrama, &QPushButton::clicked, [&]() {
            auto filmeDupaGen = service.filtreazaDupaGen("American Action Drama");
            string numarFilme = std::to_string(filmeDupaGen.size());
            QMessageBox::warning(this, "Numar de filme", "Numarul de filme cu genul American Action Drama este: " + QString::fromStdString(numarFilme));
        });
        */
    }

    void loadData() {
        lista->clear();
        auto filme = service.getAll();
        for (const auto& film : filme) {
            QString item = QString::fromStdString(film.getTitluFilm() + ", " + film.getGenFilm() + ", " +
                std::to_string(film.getAnAparitie()) + ", " + film.getActorPrincipal());
            lista->addItem(item);
        }
    }

	void initGUI() {
        QHBoxLayout* layoutPrincipal = new QHBoxLayout{};
        setLayout(layoutPrincipal);

        layoutPrincipal->addWidget(lista);

        auto layoutDreapta = new QVBoxLayout;

        auto formLayout = new QFormLayout;
        formLayout->addRow("Titlu", titluEdit);
        formLayout->addRow("Gen", genEdit);
        formLayout->addRow("An Aparitie", anAparitieEdit);
        formLayout->addRow("Actor Principal", actorPrincipalEdit);
        layoutDreapta->addLayout(formLayout);
        
        auto layoutButoaneCerintaLab = new QHBoxLayout{};
        
        //layoutButoaneCerintaLab->addWidget(butonActiune);
        //layoutButoaneCerintaLab->addWidget(butonHistoricDrama);
        //layoutButoaneCerintaLab->addWidget(butonScienceFiction);
        //layoutButoaneCerintaLab->addWidget(butonAmericanActionDrama);

        map<string, int> dictFilme;
        auto filme = service.getAll();
        for (const auto& film : filme) {
            QString item = QString::fromStdString(film.getGenFilm());
            if (dictFilme[item.toStdString()] == 0) {
                QPushButton* buton = new QPushButton{ item };
                QObject::connect(buton, &QPushButton::clicked, [&]() {
                    //auto filmeDupaGen = service.filtreazaDupaGen(item.toStdString());
                    //string numarFilme = std::to_string(filmeDupaGen.size());
                    QMessageBox::warning(this, "Numar de filme", "Numarul de filme este: " + QString::fromStdString(dictFilme[item.toStdString()]));
                });
                layoutButoaneCerintaLab->addWidget(buton);
            }
            dictFilme[item.toStdString()] ++;
        }
        
        auto layoutButoane = new QHBoxLayout{};
        layoutButoane->addWidget(butonAdauga);
        layoutButoane->addWidget(butonSterge);
        layoutButoane->addWidget(butonModifica);
        layoutButoane->addWidget(butonCauta);
        layoutButoane->addWidget(butonFiltreaza);
        layoutButoane->addWidget(butonSorteaza);
        layoutButoane->addWidget(butonUndo);
        layoutButoane->addWidget(butonExit);

        layoutDreapta->addLayout(layoutButoaneCerintaLab);
        layoutDreapta->addLayout(layoutButoane);
        layoutPrincipal->addLayout(layoutDreapta);

        
	}
};