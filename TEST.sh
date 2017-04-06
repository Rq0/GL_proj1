#!/bin/bash
echo ""
echo "-------------"
echo "Testing start"
echo "-------------"

TEST_PASS=0

PASSED_TESTS=0

FAILED_TESTS=0

testStart(){
echo $1
./RUN.sh "$1"
SYSTEM_EXIT=$?

if [ "$SYSTEM_EXIT" == "$2" ]
	echo ""
	then
		echo "Test is OK. Exit code: $SYSTEM_EXIT"
			((PASSED_TESTS++))
	else
		echo "Test is FAIL. Exit code: ($SYSTEM_EXIT) need value $2"
			((FAILED_TESTS++))
	echo ""
	TEST_PASS=1
fi

}

testStart "" 0

echo "-------------"

testStart "-h" 0

echo "-------------"

testStart "-login XXX -pass XXX" 1

echo "-------------"

testStart "-login jdoe -pass XXX" 2

echo "-------------"

testStart "-login jdoe -pass sup3rpaZZ" 0

echo "-------------"

testStart "-login jdoe -pass sup3rpaZZ -role READ -res a" 0

echo "-------------"

testStart "-login jdoe -pass sup3rpaZZ -role READ -res a.b" 0

echo "-------------"

testStart "-login jdoe -pass sup3rpaZZ -role XXX -res a.b" 3

echo "-------------"

testStart "-login XXX -pass sup3rpaZZ -role READ -res XXX" 4

echo "-------------"

testStart "-login jdoe -pass sup3rpaZZ -role WRITE -res a" 4

echo "-------------"

testStart "-login jdoe -pass sup3rpaZZ -role WRITE -res a.bc" 4

echo "-------------"

testStart "-login jdoe -pass sup3rpaZZ -role READ -res a.b -ds 2017-01-01 -de 2017-12-31 -vol 100" 0

echo "-------------"

testStart "-login jdoe -pass sup3rpaZZ -role READ -res a.b -ds 01-05-2017 -de 2017-12-31 -vol 100" 5

echo "-------------"

testStart "-login jdoe -pass sup3rpaZZ -role READ -res a.b -ds 2017-05-01 -de 2017-12-31 -vol XXX" 5

echo "-------------"

testStart "-login X -pass X -role READ -res X -ds 2015-01-01 -de 2015-12-31 -vol XXX" 1

echo "-------------"

testStart "-login X -pass X -role READ -res X" 1

echo "------------"

if [ "$TEST_PASS" = 0 ]
	then
		echo "All count OK tests: $PASSED_TESTS"
		else
			echo "Some test FAIL! FAILED tests: $FAILED_TESTS"
fi

exit $TEST_PASS