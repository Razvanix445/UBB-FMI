#include "FilmGUI.h"
#include <QtWidgets/QApplication>
#include <QtWidgets/qlabel.h>
#include <QtWidgets/qwidget.h>
#include <QtWidgets/qpushbutton.h>
#include <QtWidgets/qboxlayout.h>
#include <QtWidgets/qlineedit.h>
#include <QtWidgets/qformlayout.h>
#include <QtWidgets/qlistwidget.h>
#include <QFormLayout>

using std::vector;
using std::string;

int main(int argc, char* argv[])
{
    QApplication app(argc, argv);
    Repo repository;
    RepoFile repositoryFile{ "filme.txt" };
    Validator validator;
    CosInchirieriFilme cosInchirieri;
    Service service{ repositoryFile, validator, cosInchirieri };
    FilmGUI gui{ service };
    gui.show();

    return app.exec();
}
