VERSION = 0.1

# paths
PREFIX = /usr/local
MANPREFIX = ${PREFIX}/share/man

INCS =
LIBS =
CFLAGS = -pedantic -Wall -Os ${INCS}
LDFLAGS = -s ${LIBS}
OBJ_NAME = logic


CC = g++
