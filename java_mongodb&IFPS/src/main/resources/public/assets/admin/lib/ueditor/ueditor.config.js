/**
 * ueditor complete configuration items
 * You can configure the characteristics of the entire editor here
 */
/**************************hint********************** **********
 * All commented configuration items are UEditor default values.
 * To modify the default configuration, please first make sure that the true purpose of the parameter has been fully clarified.
 * There are two main modification schemes, one is to cancel the comment here, and then modify it to the corresponding parameter; the other is to pass in the corresponding parameter when instantiating the editor.
 * When upgrading the editor, you can directly use the old version of the configuration file to replace the new version of the configuration file, do not worry about the lack of parameters required by the new function in the old version of the configuration file will cause the script to report an error.
 **************************hint*********************** *********/

(function () {

    /**
     * The root path of the editor resource file. What it means is: take the editor instantiated page as the current path, and point to the path of the editor resource file (ie, dialog and other folders).
     * In view of the various path problems that many students have when using the editor, it is strongly recommended that you use the "relative path relative to the root directory of the website" for configuration.
     * "Relative path relative to the root of the website" is a path that starts with a slash and is like "/myProject/ueditor/".
     * If there are multiple pages in the site that are not at the same level and need to instantiate the editor, and the same UEditor is referenced, the URL here may not apply to the editor of each page.
     * Therefore, UEditor provides a root path that can be individually configured for different page editors. Specifically, write the following code at the top of the page where the editor needs to be instantiated. Of course, the URL here needs to be equal to the corresponding configuration.
     * window.UEDITOR_HOME_URL = "/xxxx/xxxx/";
     */
    var URL = window.UEDITOR_HOME_URL || getUEBasePath();

    /**
     * Configuration item main body. Note that all the configuration related to the path here do not omit the URL variable.
     */
    window.UEDITOR_CONFIG = {

        UEDITOR_HOME_URL: URL

        , serverUrl: URL + "php/controller.php"

        , toolbars: [[
            'fullscreen', 'source', '|', 'undo', 'redo', '|',
            'bold', 'italic', 'underline', 'fontborder', 'strikethrough', 'superscript', 'subscript', 'removeformat', 'formatmatch', 'autotypeset', 'blockquote', 'pasteplain', '|', 'forecolor', 'backcolor', 'insertorderedlist', 'insertunorderedlist', 'selectall', 'cleardoc', '|',
            'rowspacingtop', 'rowspacingbottom', 'lineheight', '|',
            'customstyle', 'paragraph', 'fontfamily', 'fontsize', '|',
            'directionalityltr', 'directionalityrtl', 'indent', '|',
            'justifyleft', 'justifycenter', 'justifyright', 'justifyjustify', '|', 'touppercase', 'tolowercase', '|',
            'link', 'unlink', 'anchor', '|', 'imagenone', 'imageleft', 'imageright', 'imagecenter', '|',
            'simpleupload', 'insertimage', 'emotion', 'scrawl', 'insertvideo', 'music', 'attachment', 'map', 'gmap', 'insertframe', 'insertcode', 'webapp', 'pagebreak', 'template', 'background', '|',
            'horizontal', 'date', 'time', 'spechars', 'snapscreen', 'wordimage', '|',
            'inserttable', 'deletetable', 'insertparagraphbeforetable', 'insertrow', 'deleterow', 'insertcol', 'deletecol', 'mergecells', 'mergeright', 'mergedown', 'splittocells', 'splittorows', 'splittocols', 'charts', '|',
            'print', 'preview', 'searchreplace', 'help', 'drafts'
        ]]
    };

    function getUEBasePath(docUrl, confUrl) {

        return getBasePath(docUrl || self.document.URL || self.location.href, confUrl || getConfigFilePath());

    }

    function getConfigFilePath() {

        var configPath = document.getElementsByTagName('script');

        return configPath[ configPath.length - 1 ].src;

    }

    function getBasePath(docUrl, confUrl) {

        var basePath = confUrl;


        if (/^(\/|\\\\)/.test(confUrl)) {

            basePath = /^.+?\w(\/|\\\\)/.exec(docUrl)[0] + confUrl.replace(/^(\/|\\\\)/, '');

        } else if (!/^[a-z]+:/i.test(confUrl)) {

            docUrl = docUrl.split("#")[0].split("?")[0].replace(/[^\\\/]+$/, '');

            basePath = docUrl + "" + confUrl;

        }

        return optimizationPath(basePath);

    }

    function optimizationPath(path) {

        var protocol = /^[a-z]+:\/\//.exec(path)[ 0 ],
            tmp = null,
            res = [];

        path = path.replace(protocol, "").split("?")[0].split("#")[0];

        path = path.replace(/\\/g, '/').split(/\//);

        path[ path.length - 1 ] = "";

        while (path.length) {

            if (( tmp = path.shift() ) === "..") {
                res.pop();
            } else if (tmp !== ".") {
                res.push(tmp);
            }

        }

        return protocol + res.join("/");

    }

    window.UE = {
        getUEBasePath: getUEBasePath
    };

})();
