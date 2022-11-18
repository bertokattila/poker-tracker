doLoad() {
    kubectl run -i --tty load-generator --rm --image=bash --restart=Never -- /usr/local/bin/bash -c "
    for ((i = 0 ; i < 100000 ; i++));
    do
        wget -q -O- http://web-service-service
    done
    "
}
time doLoad;


