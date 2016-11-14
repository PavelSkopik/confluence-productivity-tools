require([
        'ajs',
        'ProductivityTools/PluginSettingsManager'
    ],
    function (AJS, PluginSettingsManager) {
        AJS.toInit(function ($) {

            PluginSettingsManager.init();

        });
    }
);