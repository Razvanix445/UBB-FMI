#pragma once

#include <QtWidgets/QMainWindow>
#include "ui_Lab11.h"

class Lab11 : public QMainWindow
{
    Q_OBJECT

public:
    Lab11(QWidget *parent = nullptr);
    ~Lab11();

private:
    Ui::Lab11Class ui;
};
