.PHONY: all test clean

# ---------------------------------------------------------------------------- #

all: test

test:
	@$(MAKE) -C test run

clean:
	@$(MAKE) -C test clean
