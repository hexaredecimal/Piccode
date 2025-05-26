

echo "[LOG]: Starting"
ls ./examples/*.pics | while read line 
do
	printf "[LOG]: Running file %s\n" $line
	java -jar target/PiccodeScript-0.1-jar-with-dependencies.jar run $line > /dev/null
done

echo "[LOG]: Done"


