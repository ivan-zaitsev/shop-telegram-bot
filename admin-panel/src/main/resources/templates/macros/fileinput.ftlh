<#macro fileinput id photo>

    <link rel="stylesheet" href="static/css/fileinput.css">
    <script src="static/js/fileinput.js"></script>

    <script>
        $(document).ready(function() {
            $('#${id}').fileinput({
                browseClass: "btn btn-primary btn-block",
                showRemove: false,
                showClose: false,
                showUpload: false,
                maxFileSize: 1500,
                allowedFileExtensions: ["jpg", "png"],
                <#if photo?has_content>
                    initialPreview: ['${photo}'],
                    initialPreviewAsData: true,
                    initialPreviewConfig: [{type: "image", key: 1, showRemove: false}]
                </#if>
            });
        });
    </script>

</#macro>
