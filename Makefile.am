# Default install location
bindir = $(prefix)

# These are the targets
bin_PROGRAMS = 
bin_SCRIPTS = picoc std

all:
	mvn package

install-exec-hook:
	mkdir -p $(DESTDIR)$(bindir)
	cp ./picoc $(DESTDIR)$(bindir)/
	cp -r ./std   $(DESTDIR)$(bindir)/

clean:
	mvn clean
	rm -f picoc std

