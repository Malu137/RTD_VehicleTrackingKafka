
PGREP=/usr/bin/pgrep
JAVA=/usr/bin/java
MATHJARFILE=/BusFeed_Speed-0.0.1-SNAPSHOT.jar
ZERO=0
OKMSG=OK


#Colors
RED='\033[1;31m'
GREEN='\033[1;32m'
YELLOW='\033[1;33m'
BLUE='\033[1;34m'
NC='\033[0m' #No Color

# Start the MATH
start() {
    echo ""		
    echo "Starting RTD Feed..."
    #Verify if the service is running
    $PGREP -f RTD > /dev/null
    VERIFIER=$?
    if [ $ZERO -eq $VERIFIER ]
    then
        echo -e "The service is ${YELLOW}already${NC} running"
    else
        #Run the jar file MATH service
        $JAVA -jar "-Dfile.encoding=UTF-8" $APPDIR$MATHJARFILE q > /dev/null 2>&1 &
        #sleep time before the service verification
        sleep 3
        #Verify if the service is running
        $PGREP -f MATH  > /dev/null
        VERIFIER=$?
        if [ $ZERO -eq $VERIFIER ]
        then
            echo -e "Service was ${GREEN}successfully${NC} started"
        else
            echo -e "${RED}Failed${NC} to start service"
        fi
    fi
    echo
}

# Stop the RTDApp
stop() {
    echo ""		
    echo "Stopping RTD..."
    #Verify if the service is running
    $PGREP -f RTD > /dev/null
    VERIFIER=$?
    if [ $ZERO -eq $VERIFIER ]
    then        
		#Kill the pid of java with the service name
		kill -9 $($PGREP -f RTD)
		#Sleep time before the service verification
		sleep 3
		#Verify if the service is running
		$PGREP -f RTD  > /dev/null
		VERIFIER=$?
		if [ $ZERO -eq $VERIFIER ]
		then
			echo -e "${RED}Failed${NC} to stop service"
		else
			echo -e "Service was ${GREEN}successfully${NC} stopped"
		fi        
    else
        echo -e "The service is ${YELLOW}already${NC} stopped"
    fi
    echo
}

# Verify the status of RTD
status() {
    echo ""		
    echo "Checking status of RTD..."
    img
    #Verify if the service is running
    $PGREP -f RTD > /dev/null
    VERIFIER=$?
    if [ $ZERO -eq $VERIFIER ]
    then
        echo -e "Service is ${GREEN}running${NC}"        
    else
        echo -e "Service is ${RED}stopped${NC}"
    fi
    echo
}

# Main logic from script
case "$1" in
    start)
        start
        ;;
    stop)
        stop
        ;;
    status)
        status
        ;;
    restart|reload)
        stop
        start
        ;;
  *)
    echo "${RED}Invalid arguments${NC}"
    echo " Usages: $0 ${BLUE}{ start | stop | status | restart | reload }${NC}"
    exit 1
esac
exit 0