/* filepath: src/main/resources/static/js/invoice-form.js */
$(document).ready(function() {
    // Add row button
    $('#addRow').click(function() {
        let rowCount = $('.item-row').length;
        let newRow = $('.item-row:first').clone();
        
        // Clear values and update name attributes
        newRow.find('input').each(function() {
            let name = $(this).attr('name');
            name = name.replace(/\[\d+\]/, '[' + rowCount + ']');
            $(this).attr('name', name).val('');
        });
        
        // Set defaults
        newRow.find('.item-quantity').val(1);
        newRow.find('.item-price').val(0);
        newRow.find('.item-discount').val(0);
        newRow.find('.item-total').text('0.00');
        
        // Enable delete button
        newRow.find('.delete-row').prop('disabled', false);
        
        $('#itemsTable tbody').append(newRow);
        updateTotals();
    });
    
    // Delete row
    $(document).on('click', '.delete-row', function() {
        $(this).closest('tr').remove();
        updateRowIndices();
        updateTotals();
    });
    
    // Calculate totals on input change
    $(document).on('input', '.item-quantity, .item-price, .item-discount', function() {
        updateTotals();
    });
    
    // Form submission - FIXED FOR PROPER REDIRECTION
    $('#invoiceForm').submit(function(e) {
        e.preventDefault();
        
        // Show loading state
        const submitBtn = $('#submitInvoice');
        const originalText = submitBtn.text();
        submitBtn.prop('disabled', true).text('Processing...');
        
        let invoiceData = {
            name: $('#name').val(),
            email: $('#email').val(),
            items: []
        };
        
        // Gather all items
        $('.item-row').each(function() {
            let item = {
                itemName: $(this).find('.item-name').val(),
                quantity: parseFloat($(this).find('.item-quantity').val()),
                unitPrice: parseFloat($(this).find('.item-price').val()),
                discount: parseFloat($(this).find('.item-discount').val()) || 0
            };
            invoiceData.items.push(item);
        });
        
        // Submit to API
        $.ajax({
            url: '/Invoice',
            type: 'POST',
            contentType: 'application/json',
            data: JSON.stringify(invoiceData),
            success: function(response) {
                alert('Invoice created and emailed successfully!');
                
                // FIX: Check response format and redirect to homepage
                // if ID not found
                try {
                    const id = response.invoiceId || response.id;
                    if (id) {
                        window.location.href = '/invoices/' + id;
                    } else {
                        window.location.href = '/'; // Redirect to home if no ID
                    }
                } catch (e) {
                    // Fallback to homepage if any issue
                    window.location.href = '/';
                }
            },
            error: function(xhr) {
                submitBtn.prop('disabled', false).text(originalText);
                alert('Error creating invoice: ' + (xhr.responseText || 'Unknown error'));
                // On error, stay on the form page
            }
        });
    });
    
    function updateTotals() {
        let grandTotal = 0;
        
        $('.item-row').each(function() {
            let quantity = parseFloat($(this).find('.item-quantity').val()) || 0;
            let price = parseFloat($(this).find('.item-price').val()) || 0;
            let discount = parseFloat($(this).find('.item-discount').val()) || 0;
            
            let total = quantity * price * (1 - discount/100);
            grandTotal += total;
            
            $(this).find('.item-total').text(total.toFixed(2));
        });
        
        $('#grandTotal').text(grandTotal.toFixed(2));
    }
    
    function updateRowIndices() {
        $('.item-row').each(function(index) {
            $(this).find('input').each(function() {
                let name = $(this).attr('name');
                name = name.replace(/\[\d+\]/, '[' + index + ']');
                $(this).attr('name', name);
            });
        });
    }
});