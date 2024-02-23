#!/bin/sh
. "$(dirname "$0")/env"
# List of module names
modules="couponService fulfilmentService inventoryService orderService SPAServer"

## JVM startup options 
### without java agent 
 inventoryService_JVM_options="-Xmx256m"
     orderService_JVM_options="-Xmx512m"
fulfilmentService_JVM_options="-Xmx256m"
        SPAServer_JVM_options="-Xmx256m -XX:+UseParallelGC"

### Start app with New Relic javaagent, set config file, app_name, log_file_name 
## SPAServer Serve SPA app, Java Agent is used to demo  UseParallelGC GC only.
###WORKSHOP_LAB1-1 enable java agent (uncomment the following block, check the javaagent and other system properties:
###                such as: -Dnewrelic.config.log_file_name -Dnewrelic.config.file -Dnewrelic.config.app_name
### other debug options ####
### -Dnewrelic.config.log_level=finest
### -Dnewrelic.debug=true

#  inventoryService_JVM_options="-javaagent:newrelic_agent/newrelic-agent.jar -Xmx256m -Dnewrelic.config.log_file_name=inventoryService.log  -Dnewrelic.config.file=newrelic_agent/newrelic-inventoryService.yml  -Dnewrelic.config.app_name=inventoryService"
#      orderService_JVM_options="-javaagent:newrelic_agent/newrelic-agent.jar -Xmx256m -Dnewrelic.config.log_file_name=orderService.log      -Dnewrelic.config.file=newrelic_agent/newrelic-orderService.yml      -Dnewrelic.config.app_name=orderService"
# fulfilmentService_JVM_options="-javaagent:newrelic_agent/newrelic-agent.jar -Xmx256m -Dnewrelic.config.log_file_name=fulfilmentService.log -Dnewrelic.config.file=newrelic_agent/newrelic-fulfilmentService.yml -Dnewrelic.config.app_name=fulfilmentService"
#         SPAServer_JVM_options="-javaagent:newrelic_agent/newrelic-agent.jar -Xmx256m -Dnewrelic.config.log_file_name=SPAServer.log         -Dnewrelic.config.file=newrelic_agent/newrelic-SPAServer.yml         -Dnewrelic.config.app_name=SPAServer -XX:+UseParallelGC"


### CouponService is working as an external service (will not have Java agent enabled in this workshop)
###              the JVM option -XX:+UseParallelGC is to set the JVM garbage collector to Parallel garbage collector
couponService_JVM_options=" -Xmx256m -XX:+UseParallelGC "

check_java_version() {
    if ! command -v java >/dev/null 2>&1; then
        echo "Error: Java is not installed or not in the PATH"
        exit 1
    fi

    java_version=$(java -version 2>&1 | awk -F '"' '/version/ {print $2}')
    major_version=$(echo "$java_version" | cut -d'.' -f1)
    
    if [ "$major_version" -lt 17 ]; then
        echo "Error: Java version $java_version is not supported. Version 17 or higher is required."
        exit 1
    fi
}


# Function to check if the process is running
is_process_running() {
    pgrep -f "$1" >/dev/null
}

# Function to stop a module
stop_module() {
    echo "Stopping $1..."
    if is_process_running "$1"; then
        pkill -9 -f "$1"
        echo "   $1 stopped."
    else
        echo "   $1 is not running."
    fi
}

# Function to status a module
status_module() {
    if is_process_running "$1"; then
        echo "   $1 is running."
    else
        echo "   $1 is not running."
    fi
}

# Function to start a module
start_module() {
    if is_process_running "$1"; then
        echo "   $1 is already running."
    else
        echo "   $1 is starting.."

        module_name=$1
        JVM_options="${module_name}_JVM_options"
        nohup java ${!JVM_options} -jar "$1/build/libs/$1-0.0.1-SNAPSHOT.jar" > "applogs/$1.log" 2>&1 &

        echo "      $1 started."
    fi
}

# Function to restart a module
restart_module() {
    stop_module "$1"
    start_module "$1"
}

# Function to display usage
display_usage() {
    echo "Usage: $0 [stop|start|restart] [module_name|all]"
    echo "Available modules: $modules"
}

# Check if the correct number of arguments is passed
if [ $# -ne 2 ]; then
    display_usage
    exit 1
fi

# Check if java executable exists
check_java_version

action=$1
module=$2

# Check if the action is valid
case $action in
    "stop")
        if [ "$module" = "all" ]; then
            for module_name in $modules; do
                stop_module "$module_name"
            done
        else
            stop_module "$module"
        fi
        ;;
    "start")
        if [ "$module" = "all" ]; then
            for module_name in $modules; do
                start_module "$module_name"
            done
        else
            start_module "$module"
        fi
        ;;
    "restart")
        if [ "$module" = "all" ]; then
            for module_name in $modules; do
                restart_module "$module_name"
            done
        else
            restart_module "$module"
        fi
        ;;
    "status")
        if [ "$module" = "all" ]; then
            for module in $modules; do 
                status_module "$module"
            done
        else
            status_module "$module"
        fi
        ;;        
    *)
        echo "Invalid action: $action"
        display_usage
        exit 1
        ;;
esac

