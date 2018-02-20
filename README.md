# CSPProject
### Un problème de coloration de graphe est défini par un ensemble de sommets, et un ensemble de liens entre sommets, qui indiquent qu’ils doivent recevoir des “couleurs” différentes. Ici on cherchera une solution avec le nombre de couleurs minimal donné avec chaque jeu de test (indiqué en tête des fichiers, sauf jean.col dont la coloration minimale est de 10 couleurs).

Vous devez coder :

— les classes permettant de définir un problème de contraintes (variables, domaines,
contraintes)

— les méthodes permettant de trouver une instantiation satisfiant un problème donn´e en
appliquant un algorithme de backtrack

— une méthode améliorant le backtrack en implémentant des heuristiques d’ordonnancement
de variables (au moins : variable la plus contrainte, variable avec le moins de
valeurs, et si possible valeur la moins contraignante)

— une méthode par optimisation locale (hill-climbing selon un facteur que vous préciserez).

— une méthode améliorant le backtrack avec vérification avant (forward checking)
