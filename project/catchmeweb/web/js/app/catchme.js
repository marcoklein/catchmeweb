var playerId;

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
        console.log("Requesting world update.");
        startMain();

    }, 1000);
}


/* Network */

function sendLogin() {
    playerId = "Super Ralf";
    // process login after get-view request
    Network.moduleRequest(
            "/game",
            "login",
            {
                gameId: "TEST123",
                name: "Super Ralf",
                visitor: false
            }, worldUpdateCallback
            );
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
    var resetWorld = response.resetWorld;
    if (resetWorld) {
        // clear everything
        $("#world").empty();
        // create new world with recieved size
        var width = resetWorld.width;
        var height = resetWorld.height;
        // TODO create basic field
        var world = $("#world");
        world.append($("<table>"));
    }
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