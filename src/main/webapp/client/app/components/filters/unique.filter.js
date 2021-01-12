/**
 * Filters out all duplicate items from an array by checking the specified key
 * @param [key] {string} the name of the attribute of each object to compare for uniqueness
 if the key is empty, the entire object will be compared
 if the key === false then no filtering will be performed
 * @return {array}
 */
// Acepta dos niveles de filtrado. Ejemplo en fuentes.form.modal.html
angular.module('app').filter('unique', function () {

    return function (items, filterOn, filterOnSecondLevel) {

        if (filterOn === false) {
            return items;
        }

        if ((filterOn || angular.isUndefined(filterOn)) && angular.isArray(items)) {
            var hashCheck = {}, newItems = [];

            var extractValueToCompare = function (item) {
                if (angular.isObject(item) && angular.isString(filterOn)) {
                    if (filterOnSecondLevel && angular.isString(filterOnSecondLevel)) {
                        if (item[filterOn]) {
                            return item[filterOn][filterOnSecondLevel];
                        } else {
                            return item[filterOn];
                        }
                        // FIXME: Creo que está añadiendo los elementos vacíos también
                    } else {
                        return item[filterOn];
                    }
                } else {
                    return item;
                }
            };

            angular.forEach(items, function (item) {
                var valueToCheck, isDuplicate = false;

                for (var i = 0; i < newItems.length; i++) {
                    if (angular.equals(extractValueToCompare(newItems[i]), extractValueToCompare(item))) {
                        isDuplicate = true;
                        break;
                    }
                }
                if (!isDuplicate) {
                    newItems.push(item);
                }

            });
            items = newItems;
        }
        return items;
    };
});
