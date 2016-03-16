var TEST = true;
var DEFAULT_PAGE = "/home";
var APPLY_DEFAULT_PAGE = function () {return false;};


/*** Network ***/

/**
 * TODO replace all methods by this class and method
 *
 * @type type
 */
var Network = {
    moduleRequest: function (url, action, data, successCallback, errorCallback) {
        if (!data)
            data = {}; // always send data
        $.ajax({
            url: url,
            type: "POST",
            contentType: "application/x-www-form-urlencoded",
            data: {
                "action": action,
                "data": JSON.stringify(data)
            },
            success: function (response) {
                console.log("Ajax response: " + JSON.stringify(response));
                try {
                    successCallback(response);
                } catch (ex) {
                    // success callback is optional
                }
            },
            error: function (jqXHR, textStatus, errorThrown) {
                //alert("An ajax error occured. Please contact the site administrator.");
                console.error(errorThrown);
                console.error(textStatus);
                try {
                    errorCallback(textStatus);
                } catch (ex) {
                    // error callback is optional
                }
            }
        });
    },
    moduleActionRequest: function (url, action, successCallback, errorCallback) {
        this.moduleRequest(url, action, {}, successCallback, errorCallback);
    }
};



/*** Content Manager ***/

var ContentManager = {
    currentContent: null,
    /**
     * Changes content by requesting html of given content (normally url to a module).
     * If data is provided the data will be sent with the request.
     *
     * @param {type} content
     * @param {type} data
     * @returns {undefined}
     */
    changeContent: function (content, data) {
        this.changeContentSelector("#ajax-content", content, data);
    },
    changeContentSelector: function (selector, content, data) {
//        this.currentContent = content;
        // different content or no current content
        if (data) {
            // store data properly
            var newData = {
                "action": "get-view",
                "data": JSON.stringify(data)
            };
            console.info("Requesting get-view from " + content);
            //AjaxPage.loadContentCallback("#ajax-content", newData, this.contentCallback);
            $(selector).load(content, newData, this.contentCallback);
        } else {
            $(selector).load(content, this.contentCallback);
        }
    },
    /**
     * Callback function for changeContent(content, data).
     *
     * @param {type} response
     * @param {type} status
     * @param {type} xhr
     * @returns {undefined}
     */
    contentCallback: function (response, status, xhr) {
        if (status === "success") {
            // fade in
            $(".fade-content").hide();
            $(".fade-content").fadeIn(400, function () {
                CenterContent.center();
            });
            // apply autofocus
            $("[autofocus]").focus();
            // sometimes gaps do not resize properly - if so resize them here
            // to be sure the footer is at the right position
            setTimeout(function () {
                CenterContent.center();
            }, 1);
            // per default ajaxfy links
            ContentManager.ajaxfyLinks();
        } else {
            console.error("Error " + status + ", " + xhr + ", " + response);
            console.log("Switching to default page.");
            ContentManager.updatePageURLParameter(DEFAULT_PAGE);
            ContentManager.showAjaxPage();
            //alert("An content error occured. Please contact the site administrator.");
        }
    },
    /**
     * Shows the page which is defined in the url parameter "page".
     * 
     * An optional parameter page can be set to go directly to this page.
     *
     * @param {string} page [optional]
     *
     * @returns {undefined}
     */
    showAjaxPage: function (page) {
        if (page) {
            this.updatePageURLParameter(page);
        } else {
            // load page specified by url paremeter
            page = this.getPageURLParameter();
        }
        if (page && page.length > 0 && page.charAt(0) != "/") {
            page = "/" + page; // always start with valid link
        }
        console.log("Showing Ajax page for page param '" + page + "'");
        if (!page || page === "" || page === DEFAULT_PAGE) {
            // no page parameter given or default page - apply default page
            console.log("Applying default page.");
            page = DEFAULT_PAGE;
            if (APPLY_DEFAULT_PAGE()) return;
        } else {
            // evaluate page param
            // recreate page url
            page = page.substr(1);
            page = page.replace("-", "/");
            page = "/" + page;
        }
        console.log("Showing ajax page " + page);
        this.changeContent(page);
        CenterContent.center();
    },
    /**
     * Rewrites link logic of all links with a relation "ajax" to properly
     * call changeContent() if the user clicks on a link.
     *
     * @returns {undefined}
     */
    ajaxfyLinks: function () {
        $("a[rel='ajax']").off("click");
        $("a[rel='ajax']").on("click", function () {
            // apply content
            ContentManager.changeContent($(this).attr("href"));

            // update url parameter
            var url = $(this).attr("href");
            ContentManager.updatePageURLParameter(url);

            return false;
        });
        console.log("Ajaxfied links.");
    },
    /**
     * Updates the page url parameter "page" to given value.
     *
     * @param {type} newPage
     * @returns {String}
     */
    updatePageURLParameter: function (newPage) {
        // cut of first slash
        newPage = newPage.substring(1, newPage.length);
        // replace "/" by "-"
        newPage = newPage.replace("/", "-");
        var newUrl = this.updateURLParameter(window.location.href, "page", newPage);
        console.log("Updated page url to " + newPage + ": " + newUrl);
        var currentPageParam = this.getPageURLParameter();
        if (currentPageParam === "" && newPage === DEFAULT_PAGE.substring(1)) {
            console.log("No page param change since we are still on the home page.");
        } else {
            if (newUrl !== window.location) {
                window.history.pushState({path: newUrl}, "", newUrl);
            }
        }
        return newUrl;
    },
    getPageURLParameter: function () {
        return this.getURLParameter("page");
    },
    getURLParameter: function (name) {
        var value = decodeURIComponent((RegExp(name + '=' + '(.+?)(&|$)').exec(location.search) || [, ""])[1]);
        return (value !== 'null') ? value : false;
    },
    updateURLParameter: function (url, param, paramVal) {
        var TheAnchor = null;
        var newAdditionalURL = "";
        var tempArray = url.split("?");
        var baseURL = tempArray[0];
        var additionalURL = tempArray[1];
        var temp = "";

        if (additionalURL) {
            var tmpAnchor = additionalURL.split("#");
            var TheParams = tmpAnchor[0];
            TheAnchor = tmpAnchor[1];
            if (TheAnchor)
                additionalURL = TheParams;

            tempArray = additionalURL.split("&");

            for (i = 0; i < tempArray.length; i++) {
                if (tempArray[i].split('=')[0] !== param) {
                    newAdditionalURL += temp + tempArray[i];
                    temp = "&";
                }
            }
        } else {
            var tmpAnchor = baseURL.split("#");
            var TheParams = tmpAnchor[0];
            TheAnchor = tmpAnchor[1];

            if (TheParams)
                baseURL = TheParams;
        }

        if (TheAnchor)
            paramVal += "#" + TheAnchor;

        var rows_txt = temp + "" + param + "=" + paramVal;
        return baseURL + "?" + newAdditionalURL + rows_txt;
    }
};
ContentManager.ajaxfyLinks();

/* Override backbutton */
$(window).bind('popstate', function () {
    ContentManager.showAjaxPage();
});




/**
 * Used for content centering.
 * Use center-content-* classes in to center content in parent.
 * 
 * Available options:
 * 
 * center-content-0
 * center-content-1
 * center-content-2
 * center-content-3
 * center-content-4
 * center-content-5
 * center-content-6
 * center-content-7
 * center-content-8
 * center-content-9
 * 
 * @param {type} param
 */
var CenterContent = {
    /**
     * Centers all elements with classes center-content-*.
     * 
     * @returns {undefined}
     */
    center: function () {
        var min = 0;
        var max = 9;
        for (var gapDistribution = min; gapDistribution <= max; gapDistribution++) {
            // center each content according to its content distribution
            $(".center-content-" + gapDistribution).each(function (index) {
                var element = $(this);
                var parent = element.parent();
                // get heights
                var elementHeight = element.height();
                var parentHeight = parent.height();
                console.log("CenterContent: Parent height=" + parentHeight + ", container height=" + elementHeight);
                
                // add a ghost object if necessary which is resized to center content
                var ghostElement = element.prev(".center-gap");
                console.log("CenterContent: Ghost element=" + ghostElement);
                if (ghostElement.length <= 0) {
                    // no gap
                    // create gap
                    ghostElement = $("<div class='center-gap'>");
                    element.before(ghostElement);
                    console.log("CenterContent: Created gap object.");
                }
                
                if (elementHeight >= parentHeight) {
                    // element is higher than parent - no centering possible
                    console.log("CenterContent: No centering, content is higher than parent.");
                    ghostElement.height(0);
                    return;
                }
                
                var gapHeight = (parentHeight - elementHeight) * gapDistribution * 0.1;
                ghostElement.height(gapHeight);
                console.log("CenterContent: Gap height=" + gapHeight);
            });
            
            
        }
    }
};

$(window).resize(function() {
    console.log("Resize ajax content height=" + $("#ajax-content").height());
    CenterContent.center();
});


/*** Init ***/

$(document).ready(function () {

    ContentManager.showAjaxPage();

    console.log("webpage loaded and initialized");
});
