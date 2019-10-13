
all: run

%.class: %.java
	@javac $<

run: Van.class
	@java Van

clean:
	@rm -f *.class
