/*
2. Inchiriere filme
Creati o aplicatie care permite:
· gestiunea unei liste de filme. Film: titlu, gen, anul aparitiei, actor principal
· adaugare, stergere, modificare si afisare filme
· cautare film
· filtrare filme dupa: titlu, anul aparitiei
· sortare filme dupa titlu, actor principal, anul aparitiei + gen
*/

#include "FilmGUI.h"
#include "CosReadOnlyGUI.h"
#include "consola.h"
#include <iostream>
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
#include <qfont.h>
#include <QGraphicsOpacityEffect>
#include <qpropertyanimation.h>
#include <qstackedlayout.h>
#include <qpalette.h>
#include <QRandomGenerator>
#include <QTimer>
#include <vector>
#include <string>

using std::vector;
using std::string;

int main(int argc, char* argv[])
{
    QApplication app(argc, argv);

    //CosReadOnlyGUI PaintGUI;
    //PaintGUI.show();

    QMainWindow fereastraPrincipala;
    fereastraPrincipala.setWindowTitle("Aplicația mea");
    fereastraPrincipala.showMaximized();
    fereastraPrincipala.setMinimumSize(1550, 850);

    QPixmap backgroundImage("movieBackground.jpg");
    backgroundImage = backgroundImage.scaled(fereastraPrincipala.size(), Qt::IgnoreAspectRatio, Qt::SmoothTransformation);
    QPalette palette;
    palette.setBrush(QPalette::Window, backgroundImage);
    fereastraPrincipala.setPalette(palette);

    QPushButton* butonPornire = new QPushButton("Open FilmGUI", &fereastraPrincipala);
    QLabel* textAplicatiaMea = new QLabel("Aplicație închirieri filme", &fereastraPrincipala);
    textAplicatiaMea->setAlignment(Qt::AlignCenter);

    QFont fontButonPornire = textAplicatiaMea->font();
    fontButonPornire.setPointSize(20);
    butonPornire->setFont(fontButonPornire);

    QFont fontText = textAplicatiaMea->font();
    fontText.setPointSize(30);
    fontText.setBold(true);
    textAplicatiaMea->setFont(fontText);

    //Adaugam widgets la widgetPrincipal
    QVBoxLayout* layoutPrincipal = new QVBoxLayout(&fereastraPrincipala);
    layoutPrincipal->addWidget(textAplicatiaMea, 0, Qt::AlignCenter);
    layoutPrincipal->addWidget(butonPornire, 0, Qt::AlignCenter);

    QWidget* widgetPrincipal = new QWidget(&fereastraPrincipala);
    widgetPrincipal->setLayout(layoutPrincipal);
    fereastraPrincipala.setCentralWidget(widgetPrincipal);

    //Afisam fereastra
    Repo repository;
    RepoFile repositoryFile{ "filme.txt" };
    Validator validator;
    CosInchirieriFilme cosInchirieri;
    Service service{ repositoryFile, validator, cosInchirieri };
    FilmGUI* gui = new FilmGUI{ service, cosInchirieri };

    QObject::connect(butonPornire, &QPushButton::clicked, [&]() {
        gui->showMaximized();
        gui->setAttribute(Qt::WA_DeleteOnClose);
        gui->show();

        fereastraPrincipala.hide();
        });

    QFont font("Comic Sans MS");
    app.setFont(font);

    fereastraPrincipala.show();

    return app.exec();
}



/*
#include <iostream>
#include "Film.h"
#include "repo.h"
#include "teste.h"
#include "consola.h"
#include "CosFilme.h"

//#include "Lab11.h"
//#include <QtWidgets/QApplication>

int main()
{
    //QApplication a(argc, argv);

    ruleazaToateTestele();
    Repo repository;
    RepoFile repositoryFile{ "filme.txt" };
    Validator validator;
    CosInchirieriFilme cosInchirieri;
    Service service{ repositoryFile, validator, cosInchirieri };
    UI ui{ service };
    ui.ruleaza();

    //Lab11 w;
    //w.show();
    //return a.exec();
    return 0;
}
*/