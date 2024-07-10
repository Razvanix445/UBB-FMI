#pragma once
#include "TemplateVectorDinamic.h"
#include "Film.h"
#include "repo.h"
#include "service.h"
#include "validator.h"
#include <assert.h>
#include <sstream>

void testRepoAdauga();

void testRepoSterge();

void testRepoModifica();

void testRepoCauta();

void testRepoCautaPozitieDupaTitlu();

void ruleazaTesteValidator();

void testServiceAdauga();

void testServiceSterge();

void testServiceModifica();

void testServiceCauta();

void testServiceSortari();

void testServiceFiltreazaDupaGenDupaAnAparitie();

void testTemplateVectorDinamic();

/*
	Apeleaza functiile de test pentru modulul repo
*/
void ruleazaTesteRepo();

/*
	Apeleaza functiile de test pentru modulul service
*/
void ruleazaTesteService();

/*
	Apeleaza functiile ruleazaTesteRepo(), ruleazaTesteValidator() si ruleazaTesteService()
	Functia principala pentru testare care va fi apelata din main().
*/
void ruleazaToateTestele();