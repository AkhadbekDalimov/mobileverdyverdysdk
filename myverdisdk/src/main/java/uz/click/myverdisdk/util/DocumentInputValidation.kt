package uz.click.myverdisdk.util

import android.util.Log
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*
import java.util.regex.Matcher
import java.util.regex.Pattern

class DocumentInputValidation {

    companion object {
        fun isInputValid(documentInputType: DocumentInputType): Boolean {

            when (documentInputType) {
                is DocumentInputType.BIRTHDAY -> {
                    Log.d(
                        "DocumentInputTag",
                        "DocumentType: ${documentInputType::class.java.name},  ${documentInputType.text}"
                    )
                    val format = SimpleDateFormat("dd.MM.yyyy", Locale.ROOT);
                    val isValidDate = try {
                        format.parse(documentInputType.text);
                        true
                    } catch (e: ParseException) {
                        return false
                    }
                    return documentInputType.text.length == 10 && isValidDate
                }
                is DocumentInputType.PASSPORT -> {
                    Log.d(
                        "DocumentInputTag",
                        "DocumentType: ${documentInputType::class.java.name},  ${documentInputType.text}"
                    )
                    val pattern: Pattern = Pattern.compile("[A-Z]{2}[0-9]{7}")
                    val matcher: Matcher = pattern.matcher(documentInputType.text)
                    Log.d(
                        "DocumentInputTag1",
                        "Length: ${documentInputType.text.length >= 9},  ${matcher.matches()}"
                    )
                    return documentInputType.text.length >= 9 && matcher.matches()
                }
                is DocumentInputType.EXPIRATION -> {
                    Log.d(
                        "DocumentInputTag",
                        "DocumentType: ${documentInputType::class.java.name},  ${documentInputType.text}"
                    )
                    val format = SimpleDateFormat("dd.MM.yyyy", Locale.ROOT);
                    val isFormatValid = try {
                        format.parse(documentInputType.text);
                        true
                    } catch (e: ParseException) {
                        return false
                    }
                    val inputDate = try {
                        format.parse(documentInputType.text)
                    } catch (exception: Exception) {
                        return false
                    }

                    return documentInputType.text.length == 10 && inputDate.after(Calendar.getInstance().time) && isFormatValid
                }
            }
        }
    }
}