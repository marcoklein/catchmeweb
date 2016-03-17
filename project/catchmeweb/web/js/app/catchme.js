var playerIds;

/* Main loop */
var finish = false;
startMain();
function startMain() {
    if (finish) {
        finish = false;
        return;
    }
    setTimeout(function () {
        // execute again
        startMain();


    }, 1000);
}


/* Network */

function sendLogin() {
    // process login after get-view request
    Network.moduleRequest(
            "/game",
            "login",
            {
                gameId: "TEST123",
                name: "Super Ralf",
                visitor: false
            }, loginCallback
            );
}

function loginCallback(response) {
    // get ids of players the client can control
    playerIds = response.playerIds;
    worldUpdateCallback(response.worldUpdate);
}

function sendWorldUpdate() {
    Network.moduleRequest(
            "/game",
            "world-update",
            {
                gameId: "TEST123"
            }, worldUpdateCallback
            );
}

/**
 * 
 * @param {Object} response
 * @returns {undefined}
 */
function worldUpdateCallback(response) {
    var addEntities = response.addEntities;
    if (addEntities) {
        // TODO use jquery to add entity to table
    }
    var removeEntities = response.removeEntities;
    if (removeEntities) {
        // TODO use jquery to remove entity
    }
    var updateEntities = response.updateEntities;
    
}