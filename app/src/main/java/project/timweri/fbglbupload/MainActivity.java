package project.timweri.fbglbupload;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class MainActivity extends AppCompatActivity {

    String js_content = "(function () {\n" +
            "    var fileErrorHandler = function (e) {\n" +
            "            var msg = \"\";\n" +
            "            switch (e.code) {\n" +
            "                case FileError.QUOTA_EXCEEDED_ERR:\n" +
            "                    msg = \"QUOTA_EXCEEDED_ERR\";\n" +
            "                    break;\n" +
            "                case FileError.NOT_FOUND_ERR:\n" +
            "                    msg = \"NOT_FOUND_ERR\";\n" +
            "                    break;\n" +
            "                case FileError.SECURITY_ERR:\n" +
            "                    msg = \"SECURITY_ERR\";\n" +
            "                    break;\n" +
            "                case FileError.INVALID_MODIFICATION_ERR:\n" +
            "                    msg = \"INVALID_MODIFICATION_ERR\";\n" +
            "                    break;\n" +
            "                case FileError.INVALID_STATE_ERR:\n" +
            "                    msg = \"INVALID_STATE_ERR\";\n" +
            "                    break;\n" +
            "                default:\n" +
            "                    msg = \"Unknown Error\";\n" +
            "                    break;\n" +
            "            };\n" +
            "            console.log(\"Error: \" + msg);\n" +
            "        },\n" +
            "        requestFileSystem = window.requestFileSystem || window.webkitRequestFileSystem,\n" +
            "        dropFile = function (file) {\n" +
            "            holder.ondrop({ \n" +
            "                dataTransfer: { files: [ file ] }, \n" +
            "                preventDefault: function () {} \n" +
            "            });\n" +
            "        };\n" +
            "\n" +
            "    if (!requestFileSystem) {\n" +
            "        console.log(\"FileSystem API is not supported\");\n" +
            "        return;\n" +
            "    }\n" +
            "    requestFileSystem(\n" +
            "        window.TEMPORARY, \n" +
            "        1024 * 1024, \n" +
            "        function (fileSystem) {\n" +
            "            var textFile = {\n" +
            "                    name: \"test.txt\",\n" +
            "                    content: \"hello, world\",\n" +
            "                    contentType: \"text/plain\"\n" +
            "                },\n" +
            "                imageFile = {\n" +
            "                    name: \"test.png\",\n" +
            "                    content: \"iVBORw0KGgoAAAANSUhEUgAAAAUAAAAFCAYAAACNbyblAAAAHElEQVQI12P4//8/w38GIAXDIBKE0DHxgljNBAAO9TXL0Y4OHwAAAABJRU5ErkJggg==\",\n" +
            "                    contentType: \"image/png\",\n" +
            "                    contentBytes: function () {\n" +
            "                        var byteCharacters = atob(this.content),\n" +
            "                            byteArrays = [], offset, sliceSize = 512, slice, byteNumbers, i, byteArray;\n" +
            "\n" +
            "                        for (offset = 0; offset < byteCharacters.length; offset += sliceSize) {\n" +
            "                            slice = byteCharacters.slice(offset, offset + sliceSize);\n" +
            "                            byteNumbers = new Array(slice.length);\n" +
            "                            for (i = 0; i < slice.length; i++) {\n" +
            "                                byteNumbers[i] = slice.charCodeAt(i);\n" +
            "                            }\n" +
            "                            byteArray = new Uint8Array(byteNumbers);\n" +
            "                            byteArrays.push(byteArray);\n" +
            "                        }\n" +
            "                        return byteArrays;\n" +
            "                    }\n" +
            "                };\n" +
            "\n" +
            "            // Create and drop text file\n" +
            "            fileSystem.root.getFile(\n" +
            "                textFile.name, \n" +
            "                { create: true }, \n" +
            "                function (fileEntry) {\n" +
            "                    fileEntry.createWriter(\n" +
            "                        function (fileWriter) {\n" +
            "                            fileWriter.onwriteend = function(e) {\n" +
            "                                console.log(\"Write completed (\" + textFile.name + \")\");\n" +
            "                                fileSystem.root.getFile(\n" +
            "                                    textFile.name, \n" +
            "                                    {}, \n" +
            "                                    function (fileEntry) {\n" +
            "                                        fileEntry.file(\n" +
            "                                            function (file) {\n" +
            "                                                dropFile(file);\n" +
            "                                            }, \n" +
            "                                            fileErrorHandler\n" +
            "                                        );\n" +
            "                                    }, \n" +
            "                                    fileErrorHandler\n" +
            "                                );    \n" +
            "\n" +
            "                            };\n" +
            "                            fileWriter.onerror = function(e) {\n" +
            "                                console.log(\"Write failed (\" + textFile.name + \"): \" + e.toString());\n" +
            "                            };\n" +
            "                            fileWriter.write(new Blob([ textFile.content ], { type: textFile.contentType }));\n" +
            "                        }, \n" +
            "                        fileErrorHandler\n" +
            "                    );\n" +
            "                }, \n" +
            "                fileErrorHandler\n" +
            "            );\n" +
            "\n" +
            "            // Create and drop image file\n" +
            "            fileSystem.root.getFile(\n" +
            "                imageFile.name, \n" +
            "                { create: true }, \n" +
            "                function (fileEntry) {\n" +
            "                    fileEntry.createWriter(\n" +
            "                        function (fileWriter) {\n" +
            "                            fileWriter.onwriteend = function(e) {\n" +
            "                                console.log(\"Write completed (\" + imageFile.name + \")\");\n" +
            "                                fileSystem.root.getFile(\n" +
            "                                    imageFile.name, \n" +
            "                                    {}, \n" +
            "                                    function (fileEntry) {\n" +
            "                                        fileEntry.file(\n" +
            "                                            function (file) {\n" +
            "                                                dropFile(file);\n" +
            "                                            }, \n" +
            "                                            fileErrorHandler\n" +
            "                                        );\n" +
            "                                    }, \n" +
            "                                    fileErrorHandler\n" +
            "                                );    \n" +
            "\n" +
            "                            };\n" +
            "                            fileWriter.onerror = function(e) {\n" +
            "                                console.log(\"Write failed (\" + imageFile.name + \"): \" + e.toString());\n" +
            "                            };\n" +
            "                            fileWriter.write(new Blob(imageFile.contentBytes(), { type: imageFile.contentType }));\n" +
            "                        }, \n" +
            "                        fileErrorHandler\n" +
            "                    );\n" +
            "                }, \n" +
            "                fileErrorHandler\n" +
            "            );\n" +
            "        }, \n" +
            "        fileErrorHandler\n" +
            "    );    \n" +
            "})();";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        WebView myWebView = (WebView) findViewById(R.id.webview);
        myWebView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                view.loadUrl(
                        "javascript:" + js_content);
            }
        });
        myWebView.setWebChromeClient(new WebChromeClient());
        WebSettings webSettings = myWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        this.setDesktopMode(myWebView, true);
        myWebView.loadUrl("https://www.facebook.com");
    }

    public void setDesktopMode(WebView webView,boolean enabled) {
        String newUserAgent = webView.getSettings().getUserAgentString();
        if (enabled) {
            try {
                String ua = webView.getSettings().getUserAgentString();
                String androidOSString = webView.getSettings().getUserAgentString().substring(ua.indexOf("("), ua.indexOf(")") + 1);
                newUserAgent = webView.getSettings().getUserAgentString().replace(androidOSString, "(X11; Linux x86_64)");
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            newUserAgent = null;
        }

        webView.getSettings().setUserAgentString(newUserAgent);
        webView.getSettings().setUseWideViewPort(enabled);
        webView.getSettings().setLoadWithOverviewMode(enabled);
        webView.reload();
    }
}
