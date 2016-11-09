VERSION = 0.1

# paths
PREFIX = /usr/local
MANPREFIX = ${PREFIX}/share/man

INCS = -I/usr/include/glib-2.0 -I/usr/lib/x86_64-linux-gnu/glib-2.0/include
LIBS = -lglib-2.0
CFLAGS = -pedantic -Wall -Os ${INCS}
LDFLAGS = -s ${LIBS}
OBJ_NAME = logic


CC = gcc
