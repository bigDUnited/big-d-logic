include config.mk

SRC = logic.cpp
OBJ = ${SRC:.cpp=.o}
BIN = ${OBJ:.o=}

all: options ${BIN}

options:
	@echo big-d-logic build options:
	@echo "CFLAGS   = ${CFLAGS}"
	@echo "LDFLAGS  = ${LDFLAGS}"
	@echo "CC       = ${CC}"

.cpp.o:
	@echo CC $<
	@${CC} -c ${CFLAGS} $<

${OBJ}: config.h config.mk

.o:
	@echo CC -o $@
	@${CC} -o $@ $< ${LDFLAGS}

clean:
	@echo cleaning
	@rm -f ${BIN} ${OBJ} *.o ${OBJ_NAME}

.PHONY: all options clean
