#pragma once

#include <QtWidgets/QMainWindow>
#include "ui_Lab12QT.h"

class Lab12QT : public QMainWindow
{
    Q_OBJECT

public:
    Lab12QT(QWidget *parent = nullptr);
    ~Lab12QT();

private:
    Ui::Lab12QTClass ui;
};
