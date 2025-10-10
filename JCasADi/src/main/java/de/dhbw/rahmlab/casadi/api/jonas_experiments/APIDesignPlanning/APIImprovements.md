## Verbesserungen JCasADi

- Schreibweise allgemein unübersichtlich
- StdVector-Typen (in Kombination mit Arrays) verkomplizieren (Python: Liste bilden du übergeben), z. B.\
  Java:
  ``new StdVectorMX(new MX[]{new MX(-0.5), new MX(-1.8)})``\
  vs. \
  Python: 
  ``[-0.5,-1.8]``
- Typangabe, z. B.\
  Java: 
  ``MX nu0 = new MX(0);``\
  vs.\
  Python: 
  ``nu0 = 0``
- Teilweise Funktionen nicht möglich wie in Python (oder schwer zu finden)
  - Angabe von Labels für Function Input / Output
  - Verweis auf Ausgabe einer Funktion nicht so leicht wie in Python
- Funktionsaufruf nur über call-Methoden möglich 
  - In Python Funktionsaufruf wie in Mathe\
    ``f = Function("f",[x,y],[sin(x)*y])``\
    ``f(1,2)``\
    vs.\
    ``Function f = new Function("f", new StdVectorMX(new MX[]{x, y}), new StdVectorMX(new MX[]{MX.times(MxStatic.sin(x), y)}));``\
    ``StdVectorMX input = new StdVectorMX(new MX[]{new MX(1), new MX(2)});``\
    ``StdVectorMX output = new StdVectorMX();``\
    ``f.call(input, output);``
- call-Methoden verlangen eine Variable für Output => Aufwending
  - In Python nicht nötig (siehe oben)
- Bei Operationen (+, -, *, /, sin, cos, …) immer Angabe der Klasse benötigt
  - In Python nicht nötig\
    ``A*x``\
    vs.\
    ``MX.times(A, x)``
- Typecast teilweise nicht möglich (z. B. von MX zu DM)
- Keine Möglichkeit „rootfinder, conic, nlpsol, integrator“-Methode zu befüllen
  - SWIGTYPE-Klasse existiert, um Java-Map zu repräsentieren nur Befüllen anscheinend nicht
  - Bei rootfinder & integrator Verwendung von Functions möglich
  - Nicht möglich Map Objekt zu übergeben
- Funktionen in Python können für verschiedene Typen verwendet werden
  - Änderung des Inputtypen erfordert Anpassung der Methode/Funktion
- Slicing-Syntax macht Code in Python lesbarer, wenn diese auf MX angewendet
- Extrahieren von Ergebnissen viel komplizierter\
  Python: ``print(l([-0.5, -1.8]))``\
  vs.\
  Java:\
  ``StdVectorMX result = new StdVectorMX(new MX[]{});``\
  ``l.call(new StdVectorMX(new MX[]{x0}), result);``\
  ``System.out.println(result.get(0).at(0,0));``
- Ausgaben sind verschieden, da Typen verschieden sind\
  Siehe: Java ```NonlinearProgramming.java Exercise 1.5``` und zugehörige Python Lösung Exercise 1.5
- Set-Methode für Submatrix kompliziert/unverstädnlich\
  Java: ``rhs.set(MxStatic.minus(rhs.at(3), new MX(gravity)), true, new IM(3));``\
  vs.\
  Python: ``rhs[3] = rhs[3] - g``\
  Set Methode in Java liefert anderes Ergebnis (siehe Aufgabe Nlpsol 1.1)
- Angaben können in Java nicht so wie in Python gemacht werden, um auf einen bestimmten Wert zu verweisen:\
  Python ``res["x"]`` (Solution Nlpsol.py Zeile: 86, 96, etc.)

Opti-Interface macht Code übersichtlicher, jedoch bestehen weiterhin Probleme, wie z. B. beim Erstellen von Expressions