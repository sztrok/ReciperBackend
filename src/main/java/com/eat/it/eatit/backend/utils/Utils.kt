package com.eat.it.eatit.backend.utils

import java.util.*
import java.util.function.Consumer

/**
 * Updates a field using the provided setter if the value is not null.
 *
 * @param <T> the type of the field to be updated
 * @param value the new value for the field
 * @param setter a Consumer that sets the field to the new value
 */
internal fun <T> updateField(value: T, setter: Consumer<T>) {
    Optional.ofNullable(value).ifPresent(setter)
}