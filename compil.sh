javac -cp ./classes -sourcepath ./src -d ./classes ./src/jeu/elements/*.java
javac -cp ./classes -sourcepath ./src -d ./classes ./src/jeu/*.java
javac -cp ./classes -sourcepath ./src -d ./classes ./src/client/*.java
javac -cp ./classes -sourcepath ./src -d ./classes ./src/serveur/*.java
javac -cp ./classes -sourcepath ./src -d ./classes ./src/client/ihm/*.java

#Tests
#javac -cp /usr/share/java/junit4.jar:./classestest:./classes -sourcepath ./srctest -d ./classestest ./srctest/jeu/elements/*.java
#javac -cp /usr/share/java/junit4.jar:./classestest:./classes -sourcepath ./srctest -d ./classestest ./srctest/jeu/*.java

#ATTENTION ! Lancer les tests avec le Terrain1 !!!
#java -cp /usr/share/java/junit4.jar:./classestest:./classes org.junit.runner.JUnitCore jeu.AllTests
cd classes

