# CasADi Java API Beispiel

## Vergleich der Implementierungen

Diese Datei stellt anhand eines kleinen Python-Beispiels den aktuellen Stand der API-Entwicklung dar. Dazu werden die verschiedenen Implementierungen zur Definition von symbolischen Variablen und mathematischen Funktionen in Python und Java verglichen.

### 1. Python-Code

Hier ist der ursprüngliche Python-Code:

```python
x = MxStatic.sym('x', 2)
f = x[0]**2 + tanh(x[1])**2
g = cos(x[0] + x[1]) + 0.5
h = sin(x[0]) + 0.5
lambd = MxStatic.sym('lambd') 
nu = MxStatic.sym('nu') 
lag = f + lambd * g + nu * h
```

### 2. Java-Code vor der API

Hier ist der Java-Code, der vor der Entwicklung der API verwendet wurde:

```java
var x = MxStatic.sym("x", 2);
var f_ex = MxStatic.plus(MxStatic.pow(x.at(0), new MX(2)), MxStatic.pow(MxStatic.tanh(x.at(1)), new MX(2)));
var g_ex = MxStatic.plus(MxStatic.cos(MxStatic.plus(x.at(0), x.at(1))), new MX(0.5));
var h_ex = MxStatic.plus(MxStatic.sin(x.at(0)), new MX(0.5));
var lambd = MxStatic.sym("lambd");
var nu = MxStatic.sym("nu");
var lag = MxStatic.plus(MxStatic.plus(f_ex, MxStatic.times(lambd, g_ex)), MxStatic.times(nu, h_ex));
```

### 3. Aktueller API-Code

Hier ist der aktuelle Java-Code der API, um das Python-Code-Beispiel in Java zu implementieren:

```java
var x = new MXBuilder().setName("x").setRows(2).build(); // Äquivalent: var x = MXWrapper.sym("x", 2)
var f = x.at(0).sq().add(x.at(1).tanh().sq());
var g = x.at(0).add(x.at(1)).cos().add(0.5);
var h = x.at(0).sin().add(0.5);
var lambd = new MXBuilder().setName("lambd").build(); // Äquivalent: var lambd = MXWrapper.sym("lambd")
var nu = new MXBuilder().setName("nu").build(); // Äquivalent: var nu = MXWrapper.sym("nu")
var lag = f.add(lambd.multiply(g)).add(nu.multiply(h)); 
```

## Techniken zur Erhöhung der Benutzerfreundlichkeit

Bei der Entwicklung der API werden Techniken eingesetzt, wie u. a.:

1. **Builder-Pattern**: Für die Erstellung von komplexen CasADi-Objekten, wie z. B. MX (siehe MXBuilder)

2. **Fluent Interface**: Um bestimmte Methodenaufrufe in einer lesbaren und natürlichen Weise zu verketten

3. **JSR-331 Spezifikation**: Verwendung der JSR-331 Spezifikation im weiteren Verlauf der API-Entwicklung