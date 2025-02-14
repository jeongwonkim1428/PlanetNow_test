const stompClient = new StompJs.Client({
    brokerURL: 'ws://localhost:80/websocket'
});

stompClient.onConnect = (frame) => {
    setConnected(true);
    console.log('Connected: ' + frame);
    stompClient.subscribe('/topic/messages', (message) => {
        showGreeting(JSON.parse(message.body).content);
        const $container = $('#scrollable-container');
        $container.scrollTop($container[0].scrollHeight);
    });
    stompClient.publish({
        destination: "/app/welcome",
        body: $("#nickname").val()
    });
};

stompClient.onWebSocketError = (error) => {
    console.error('Error with websocket', error);
};

stompClient.onStompError = (frame) => {
    console.error('Broker reported error: ' + frame.headers['message']);
    console.error('Additional details: ' + frame.body);
};

function setConnected(connected) {
    $("#connect").prop("disabled", connected);
    $("#disconnect").prop("disabled", !connected);
    if (connected) {
        $("#conversation").show();
    }
    else {
        $("#conversation").hide();
    }
    $("#messages").html("");
}

function connect() {
    stompClient.activate();
    $("#content").prop("disabled", false);
}

function disconnect() {
    stompClient.publish({
        destination: "/app/bye", // Existing backend endpoint
        body: $("#nickname").val() // Send the welcome message
    });
    $("#content").prop("disabled", true);

    stompClient.deactivate();
    setConnected(false);
    console.log("Disconnected");
}

function sendContent() {
    stompClient.publish({
        destination: "/app/hello",
        body: JSON.stringify({'content': $("#content").val(),
                                    'userId': $("#userId").val(),
                                    'nickname': $("#nickname").val()})
    });
    $("#content").val("");
}

function showGreeting(message) {
    $("#messages").append("<tr><td>" + message + "</td></tr>");
}

$(function () {
    $("form").on('submit', (e) => e.preventDefault());
    $( "#connect" ).click(() => connect());
    $( "#disconnect" ).click(() => disconnect());
    $( "#send" ).click(() => sendContent());
});

