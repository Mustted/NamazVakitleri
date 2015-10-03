package tr.xip.prayertimes;

import java.util.Calendar;

import static tr.xip.prayertimes.ui.app.NamazVakitleriApplication.getContext;

public class Utils {

    /**
     * Calculates and returns the dp value for an amount of pixels
     */
    public static int pxToDp(float px) {
        return (int) (px / getContext().getResources().getDisplayMetrics().density);
    }

    /**
     * Calculates and returns the pixel value for a dp value
     */
    public static int dpToPx(float dp) {
        return (int) (dp * getContext().getResources().getDisplayMetrics().density);
    }

    public static long getTimestampFromDateTime(String date, String time) {
        String[] dateParts = date.split("\\.");
        String[] timeParts = time.split(":");

        int day = Integer.parseInt(dateParts[0]);
        int month = Integer.parseInt(dateParts[1]);
        int year = Integer.parseInt(dateParts[2]);
        int hour = Integer.parseInt(timeParts[0]);
        int minute = Integer.parseInt(timeParts[1]);

        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, year);
        cal.set(Calendar.MONTH, month - 1);
        cal.set(Calendar.DAY_OF_MONTH, day);
        cal.set(Calendar.HOUR_OF_DAY, hour);
        cal.set(Calendar.MINUTE, minute);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);

        return cal.getTimeInMillis();
    }

    public static long getTimestampFromDate(String date) {
        String[] dateParts = date.split("\\.");

        int day = Integer.parseInt(dateParts[0]);
        int month = Integer.parseInt(dateParts[1]);
        int year = Integer.parseInt(dateParts[2]);

        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, year);
        cal.set(Calendar.MONTH, month - 1);
        cal.set(Calendar.DAY_OF_MONTH, day);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);

        return cal.getTimeInMillis();
    }
}
