## Erster Entwurf API Design

**Wichtig**: API so gestalten, dass sie den typischen Workflow für nicht-lineare Optimierungsprobleme vereinfacht

### Typischer Workflow eines NLP-Problems:

1. **Problemdefinition:**
    - Definiere Optimierungsvariablen \( x \), Zielfunktion \( f(x) \), und Nebenbedingungen \( g(x) \).

2. **Zielfunktion definieren:**
    - Definiere die mathematische Ausdrucksform für die Zielfunktion.

3. **Nebenbedingungen formulieren:**
    - Definiere mathematische Ausdrücke für die Nebenbedingungen und füge sie zum Optimierungsproblem hinzu.

4. **Setze Anfangswerte:**
    - Initialisiere die Optimierungsvariablen mit Startwerten.

5. **Wähle Lösungsverfahren:**
    - Wähle einen geeigneten Optimierungsalgorithmus (z.B. IPOPT).

6. **Lösen des Problems:**
    - Führe das Optimierungsproblem mit dem gewählten Solver aus.

7. **Ausgabe der Lösung:**
    - Zeige die Werte der Variablen und der Zielfunktion, die das Optimierungsproblem lösen.

8. **Analyse der Lösung:**
    - Überprüfe, ob die Lösung gültig ist und alle Nebenbedingungen erfüllt sind.

9. **Optional: Überwachung und Visualisierung:**
    - Verfolge den Fortschritt der Iterationen und visualisiere die Ergebnisse (falls gewünscht).

---

Wichtige Aspekte die beim API-Design berücksichtigt werden sollten:

### 1. **Fokus auf Variablen & Ausdrücke**

Für nicht-lineare Optimierung ist es besonders wichtig, 
dass die Benutzer die mathematischen Ausdrücke für die Zielfunktion und die Nebenbedingungen in einer Art und Weise formulieren können, 
die der Mathematik selbst nahe kommt. 

D. h.: Einfache Möglichkeit bereitstellen, Variablen zu definieren und mathematische Ausdrücke zusammenzusetzen.

**Kernfunktionen für Variablen:**
- Erstellen von Variablen (mit Anfangswerten und Grenzen).
- Bearbeiten von Variablen, wie z.B. Setzen von Startwerten und Boundaries.

**Vorschlag API-Design:**
1. `Variable`: Diese Klasse kann die Variablen der Optimierung repräsentieren. 
Sollte Methoden bereitstellen, um Variablen zu definieren und auf ihre Eigenschaften zuzugreifen.

2. **Mathematische Ausdrücke:** Die API sollte es den Nutzern ermöglichen, Ausdrücke zu erstellen, die mathematisch korrekt sind. 
Kann durch eine benutzerfreundliche Möglichkeit zur Kombination von Variablen und Operationen wie Addition, Subtraktion, 
Multiplikation und Division erreicht werden. 
Um nicht-lineare Ausdrücke zu unterstützen, sollten auch Operationen wie Potenzen, 
Exponentialfunktionen und andere mathematische Funktionen auf jeden Fall vorhanden sein.

### 2. **Zielfunktion und Nebenbedingungen**

Für NLP-Probleme besteht das Ziel darin, eine Zielfunktion zu minimieren oder zu maximieren und bestimmte Nebenbedingungen zu berücksichtigen.

**Kernfunktionen für Zielfunktionen und Nebenbedingungen:**
- Definiere eine Zielfunktion, die durch ein mathematisches Ausdrucksobjekt oder eine Methode dargestellt wird.
- Definiere Nebenbedingungen (Gleichungen und Ungleichungen).
- Arbeite mit **nicht-linearen Ausdrücken**, da die typischen NLP-Probleme oft nicht-lineare Beziehungen zwischen den Variablen haben.

**Vorschlag API-Design:**
1. `Objective`: Diese Klasse kann eine Zielfunktion repräsentieren, die in einem mathematisch korrekten Ausdruck formuliert ist. 
Sollte auch Methoden zur Manipulation der Zielfunktion enthalten.

2. `Constraint`: Diese Klasse stellt eine Nebenbedingung dar, die entweder eine Gleichung oder Ungleichung sein kann.

    - **Nebenbedingungen**: Diese könnten als Ausdrücke formuliert werden, die entweder als Gleichung (`==`) oder Ungleichung (`<=` oder `>=`) verwendet werden.

### 3. **Solver-Integration**

Für die nicht-lineare Optimierung wird eine Schnittstelle benötigt, um einen Solver zu integrieren, der das Optimierungsproblem löst.
Dafür auf Solver zurückgreifen, die von JCasADi bereitgestellt werden.

**Kernfunktionen für Solver:**
- Möglichkeit, den Solver auszuwählen (z.B. IPOPT).
- Mechanismus zum Starten und Überwachen des Lösungsprozesses.

### 4. **Lösungsprozess und Ergebnisextraktion**

Nachdem das Optimierungsproblem gelöst wurde, müssen die Benutzer auf die Ergebnisse zugreifen können, 
insbesondere auf die Werte der Variablen und ggf. die Lagrange-Multiplikatoren oder den Wert der Zielfunktion.

**API-Design für Lösungen:**
1. `Solution`: Diese Klasse sollte den Wert der Optimierungsvariablen nach der Lösung des Problems enthalten.
2. Möglichkeit, den Wert von Variablen nach dem Lösen des Problems zu extrahieren.

### 5. **Zusätzliche Überlegungen**

- **Erweiterbarkeit**: API sollte flexible sein, d. h. Integration neuer Funktionen ermöglichen
- **Benutzerfreundliche API**: Es wäre sinnvoll, Methoden hinzuzufügen, die es dem Benutzer ermöglichen, Ausdrücke einfach zu erstellen (z.B. für nicht-lineare Funktionen wie `x^2 + sin(y)` oder `exp(x + y)`).
- **Fehlerbehandlung**: Fehler- und Ausnahmebehandlungen implementieren, um sicherzustellen, dass ungültige Operationen oder Optimierungsprobleme korrekt behandelt werden.