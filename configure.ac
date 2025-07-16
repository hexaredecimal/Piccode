AC_INIT([PiccodeScript], [1.0])
AM_INIT_AUTOMAKE([foreign])
AC_PROG_MAKE_SET

# Default install prefix
: ${prefix=`pwd`/local}
AC_SUBST([prefix])

AC_MSG_CHECKING([for Java 21 or newer])
JAVA_VERSION_RAW=`java -version 2>&1 | head -n 1 | sed -E 's/.*version "([0-9]+).*"/\1/'`
case "$JAVA_VERSION_RAW" in
  ''|*[!0-9]*)
    AC_MSG_ERROR([Java not found or version is unreadable. Ensure Java 21+ is installed.])
    ;;
  *)
    if test "$JAVA_VERSION_RAW" -lt 21; then
      AC_MSG_ERROR([Java version $JAVA_VERSION_RAW found, but 21+ is required.])
    fi
    ;;
esac
AC_MSG_RESULT([Java $JAVA_VERSION_RAW found])



AC_CONFIG_FILES([Makefile])
AC_OUTPUT

