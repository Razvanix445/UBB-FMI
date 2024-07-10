#pragma once
#include <qwidget.h>
#include <qlistwidget.h>
#include <qpushbutton.h>
#include <qlabel.h>
#include <QVBoxLayout>
#include <vector>
#include "service.h"

using namespace std;

class ListaButoane : public QWidget {
	Q_OBJECT
private:
	Service& service;

};