$(document).ready(function () {
    bindEventCheckAllCheckBox('checkAll');
    enableOrDisableDeleteAll();
    autoCheckCheckBoxAll('checkAll')
})
function bindEventCheckAllCheckBox(id) {
    $('#'+id).on('change',function () {
        if((this).checked) {
            $(this).closest('table').find('input[type=checkbox]').prop('checked', true);
        }else {
            $(this).closest('table').find('input[type=checkbox]').prop('checked', false);
        }

    })

}
function enableOrDisableDeleteAll() {
    $('input[type=checkbox]').click(function () {
if($(this).attr('id')=='checkAll'&&$(this).prop('checked')==false){
    $(this).closest('table').find('input[type=checkbox]').prop('checked', false);
}
if($('input[type=checkbox]:checked').length>0){
    $('#deleteAll').prop('disabled',false);
}else {
    $('#deleteAll').prop('disabled',true);
}
    });

}
function autoCheckCheckBoxAll(id){
    var totalBox=$('#'+id).closest('table').find('tbody input[type=checkbox]').length;
    $('#'+id).closest('table').find('tbody input[type=checkbox]').each(function () {
    var tableObj=$('#'+id).closest('table');
    $(this).on('change',function () {
var totalCheck=$(tableObj).find('tbody input[type=checkbox]:checked').length;
if(totalCheck==totalBox){
    $('#'+id).prop('checked',true);
}else {
    $('#'+id).prop('checked',false);
}
    })
    })
}
    
