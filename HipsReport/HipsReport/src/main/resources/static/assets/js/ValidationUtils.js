ValidationUtils = {};


// UTILITY METHODS
/**
 * Tests to see if a string have at least n characters, and possibly
 * more than m characters in length.
 * 
 * @param string what to test
 * @param minLength {int} the minimum length of the string
 * @param maxLength {int} the maximum length of the string (optional)
 * @returns {Boolean} true if the input is a string of betweem min and 
 * max characters. 
 */
ValidationUtils.hasLengthOf = function(string, minLength, maxLength) {
  if (string && string.length) {
	if (string.length < minLength) {
	  return false;
	// Check maximum length if there is one. 
	} else if (maxLength && string.length < maxLength) {
	  return false;	
	}
	return true;
  }  
  return false;
};

/**
 * Checks that a value (can be a string or a number) represents
 * an integer between two values. 
 * 
 * @param {scalar value} input the value to test
 * @param {int} min input is valid if it is equal to or greater than this
 * @param {int} max input is valid if it is equal to or less than this
 * @returns {Boolean} if value is an int such that min <= value <= max.
 */
ValidationUtils.isNumberBetween = function(input, min, max) {
  var numValue = null;
  // Check it is a valid number first
  if (isNaN(input)) {
	return false;
  }	
  numValue = parseInt(input);
  return min <= numValue && max >= numValue;
};

// FIELD VALIDATION
/**
 * Checks that a text field or area has at least one character 
 * of input, or the field's error message is activated. The error message
 * is put in a span with the class '[fieldName]Error' and it is assumed this
 * exist. 
 * 
 * @param fieldName {String} the name of the field within the form
 * @param errorMsg {String} the error message to display if validation fails. 
 * @returns {Boolean} true if field validates, false otherwise
 */
ValidationUtils.fieldMustHaveText = function(fieldName, errorMsg) {
  var input = $('#' + fieldName), isValid = false;
  isValid = ValidationUtils.hasLengthOf(input.val(), 1);
  if (!isValid) {
	ValidationUtils.setErrorForField(fieldName, errorMsg);
  }
  return isValid;
};

/**
 * Checks that a text field or area has input between two designated lengths. 
 * The error message is put in a span with the class '[fieldName]Error' and 
 * it is assumed this exist. 
 * 
 * @param fieldName {String} the name of the field within the form
 * @param errorMsg {String} the error message to display if validation fails.
 * @param minLength {int} the minimum length of the input's value needed.
 * @param maxLength {int} the maximum length of the input needed (optional)
 *  
 * @returns {Boolean} true if field validates, false otherwise
 */
ValidationUtils.fieldMustHaveTextOfLength = function(fieldName, errorMsg, minLenght, maxLength) {
  var input = $('#' + fieldName), isValid = false;
  isValid = ValidationUtils.hasLength(input.val(), minLength, maxLength);
  if (!isValid) {
	ValidationUtils.setErrorForField(fieldName, errorMsg);
  }
  return isValid;
};

/**
 * Checks that a select/drop down menu is not on the first "Please Select"
 * type option and instead has a valid selection. 
 * 
 * @param fieldName {String} the name of the select field within the form
 * @param errorMsg {String} the error message to display if validation fails.
 */
ValidationUtils.selectMustHaveBeenUsed = function(fieldName, errorMsg) {
	var input = $('#' + fieldName), isValid = false, fieldValue = input.val();
	// Must have length and not be equal to "-"
	isValid = fieldValue && fieldValue != '' && fieldValue != '-';
	if (!isValid) {
	  ValidationUtils.setErrorForField(fieldName, errorMsg);
	}
	return isValid;
};

/**
 * Checks that the value of a given field  
 * @param fieldName the name of the field to check
 * @param errorMsg the error message to display if check fails. 
 */
ValidationUtils.luhnCheckField = function(fieldName, errorMsg) {
  var value = $('#' + fieldName + 'Input').val();
  if (value && value.length > 0) {
    if (!ValidationUtils.luhnCheck(value)) {
      ValidationUtils.setErrorForField(fieldName, errorMsg);
      return false;
    } else {
      return true; 	 
    }		
  }
};

ValidationUtils.validateDobFields = function() {
  var isValid = true, messages = [], month, day, year;
  
  if (!ValidationUtils.isNumberBetween($('#dayInput').val(), 1, 31)) {
	    messages.push('Please enter a day value between 1 and 31');	  
	    isValid = false;
	  } else {
		day = parseInt($('#dayInput').val());
	  }
  
  
  if (!ValidationUtils.isNumberBetween($('#monthInput').val(), 1, 12)) {
    messages.push('Please enter a month value between 1 and 12');	  
    isValid = false;
  } else {
	month = parseInt($('#monthInput').val());
  }
  
  year = $('#yearInput').val();
  if (!year || year.length !== 4 || isNaN(year)) {
	messages.push('Please enter a four digit value for the year');  
	isValid = false;
  } else {
	year = parseInt(year); // Convert to number for use later  
  }
  
  
  // Check that the date itself is actually valid
  // compare with today
  if (isValid) {
	  
  }
  
  if (!isValid) {
	$('#dayInput').addClass('has-error');
	$('#monthInput').addClass('has-error');
	$('#yearInput').addClass('has-error');
	$('#dobInputError').text(messages.join(', '));
  }
  
  return isValid;
};

/**
 * Validates a value for day, month and year to ensure that each make 
 * sense in relation to each other. Validation messages are added to a 
 * caller supplied array for later on screen rendering. 
 * 
 * @param day {int} the day of the date
 * @param month {int} the month of the date
 * @param year {int} the year component  
 * @param messages {Array} where validation messages are pushed to. Required.
 * 
 *  @return {Boolean} true if day/month/year combined represent a valid date. 
 */
ValidationUtils.validateDateAsWhole = function(day, month, year, messages) {
  var isValid = true;
  if (month == 2) { // February
    var max = 28;
    if (year % 4 === 0 || ((year % 100 === 0) && year % 400 === 0)) {
      max = 29;
    }
    
    if (day < 1 || day > max) {
      messages.push('Please enter a date between 1 and ' + max);
      isValid = false;
    }       
  } else if ([4, 6, 9, 11].indexOf(month) > -1) { // April, June, September, November
    if (day > 30 || day < 1) {
      messages.push('Please enter a date between 1 and 30');  
      isValid = false;
    }
  } else if (day > 31 || day < 1){ // All other months
    messages.push('Please enter a date between 1 and 31');  
    isValid = false;          	
  }	
  return isValid;
};

/**
 * Clears all form associated error messages currently displayed  
 * on the screen.
 */
ValidationUtils.clearErrorMessages = function() {
  $('div.error').text('');
  $('.has-error').removeClass('has-error');
};

/**
 * Sets an error message for a field in a form. 
 * @param fieldName {String} the name of the field within the form to show an error message for. 
 * @param errorMsg {String} the error message to display if validation fails.
 */
ValidationUtils.setErrorForField = function(fieldName, errorMsg) {
	$('div#' + fieldName + 'Error').text(errorMsg);
	$('#' + fieldName + 'Group').addClass('has-error');	
};

/**
 * Checks to see that a numeric string passes a LUHN algorithm check. 
 * @param {String} number essentially a string of numbers
 * @returns {Boolean} if the input value passes a LUHN check. 
 */
// NOTE: this code is taken from Stakeoverflow (need to find URL)
ValidationUtils.luhnCheck = function(number){
  return !/^\d+$/.test(number) || (number.split('').reduce(function(sum, d, n){ 
    return n === (number.length-1)? 0 : sum + parseInt((n%2)? d: [0,2,4,6,8,1,3,5,7,9][d]);
  }, 0)) % 10 == 0;
};

/**
 * Tests to see if a form field has a non-empty string value. 
 * 
 * @param field the id of the field to test. As is the pattern in this
 * project the field will have an id of xxxxInput. It's the xxxx bit 
 * that is needed here. 
 * 
 * @returns {Boolean}
 */
ValidationUtils.hasValue = function(field) {
  var value = $('#' + field + 'Input').val();
  return value && value.length > 0;
};

