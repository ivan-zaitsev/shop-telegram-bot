<#macro table name>
    <link rel="stylesheet" href="static/css/dataTables.bootstrap.css">
    <script src="static/js/jquery.dataTables.js"></script>
    <script src="static/js/dataTables.responsive.js"></script>
    <script src="static/js/dataTables.bootstrap.js"></script>

    <script>
        $(document).ready(function() {
            $('table#${name}').DataTable({
                "order": [[ 0, "desc" ]],
                "bDeferRender": true,
                "sPaginationType": "full_numbers",
                "oLanguage": {
                    "sLengthMenu": 'Show <select class="form-control input-sm">'+
                        '<option value="10">10</option>'+
                        '<option value="25">25</option>'+
                        '<option value="50">50</option>'+
                        '<option value="100">100</option>'+
                        '<option value="500">500</option>'+
                        '<option value="-1">All</option>'+
                        '</select> entries',
                    "oPaginate": {
                        "sFirst":    "First",
                        "sLast":     "Last",
                        "sNext":     "Next",
                        "sPrevious": "Previous"
                    }
                },
                "responsive": true
            });
        });
    </script>
</#macro>