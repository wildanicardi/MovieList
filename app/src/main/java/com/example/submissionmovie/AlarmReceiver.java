package com.example.submissionmovie;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.widget.Toast;

import androidx.core.app.NotificationCompat;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.submissionmovie.model.ListMovie;
import com.example.submissionmovie.view.DetailMovieActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class AlarmReceiver extends BroadcastReceiver {

    private static final String EXTRA_TYPE = "type";
    private static final String CHANNEL_ID_DAILY = "channel_1";
    private static final CharSequence CHANNEL_NAME_DAILY = "Dialy channel";
    private static final int NOTIFICATION_ID_DAILY = 2;
    private final static String TIME_FORMAT = "HH:mm";
    private String API_URL = "https://api.themoviedb.org/3/discover/movie?api_key=1645bb1963fcc609302208aacc323e34";

    private static int notification_request_code2 = 200;
    private int idNotification = 0;

    @Override
    public void onReceive(Context context, Intent intent) {
        int type = intent.getIntExtra(EXTRA_TYPE,MainActivity.ID_REPEATING);
        if (type==MainActivity.ID_REPEATING){ dailyReminder(context); }
        if (type==MainActivity.ID_REPEATING2){ getMovieTodayRelease(context);}
    }

    private String getTodayDate(){
        Calendar calendar = Calendar.getInstance();
        int thisYear = calendar.get(Calendar.YEAR);
        int thisMonth = calendar.get(Calendar.MONTH) + 1;
        int thisDate = calendar.get(Calendar.DATE);
        return thisYear + "-" + thisMonth + "-" + thisDate;
    }
    private void getMovieTodayRelease(final Context context) {
        String today_date = getTodayDate();
        API_URL +="&primary_release_date.gte="+today_date+"&primary_release_date.lte="+today_date;
        StringRequest stringRequest = new StringRequest(Request.Method.GET,
                API_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {

                    JSONObject jsonObject = new JSONObject(response);

                    JSONArray array = jsonObject.getJSONArray("results");
                    for (int i = 0; i < array.length(); i++){

                        ListMovie movie = new ListMovie(array.getJSONObject(i));

                        JSONObject data = array.getJSONObject(i);
                        movie.setTitle(data.getString("title"));
                        movie.setOverview(data.getString("overview"));
                        movie.setPic(data.getString("poster_path"));
                        idNotification = i;
                        makeNotif(context,movie);

                    }
                } catch (JSONException e) {

                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(context, "Error" + error.toString(), Toast.LENGTH_SHORT).show();

            }
        });

        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(stringRequest);


    }

    private void makeNotif(Context context, ListMovie movie) {

            Intent intent = new Intent(context, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            PendingIntent pendingIntent = PendingIntent.getActivity(context, notification_request_code2, intent, PendingIntent.FLAG_UPDATE_CURRENT);
            NotificationCompat.Builder mBuilder;
            NotificationManager mNotificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
            mBuilder = new NotificationCompat.Builder(context, CHANNEL_ID_DAILY)
                    .setContentTitle(movie.getTitle())
                    .setContentText(movie.getTitle() + " " + context.getResources().getString(R.string.release_today))
                    .setSmallIcon(R.drawable.ic_notifications_black_24dp)
                    .setContentIntent(pendingIntent)
                    .setAutoCancel(true);


            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {


                NotificationChannel channel = new NotificationChannel(CHANNEL_ID_DAILY, CHANNEL_NAME_DAILY, NotificationManager.IMPORTANCE_DEFAULT);

                mBuilder.setChannelId(CHANNEL_ID_DAILY);

                if (mNotificationManager != null) {
                    mNotificationManager.createNotificationChannel(channel);
                }
            }

            Notification notification = mBuilder.build();

            if (mNotificationManager != null) {
                mNotificationManager.notify(idNotification, notification);
            }
    }

    public void dailyReminder(Context context) {
        Intent intent = new Intent(context, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);
        String title= context.getResources().getString(R.string.app_name);
        String text= context.getResources().getString(R.string.dialy_notif_subtext);
        NotificationCompat.Builder mBuilder;
        NotificationManager mNotificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        mBuilder = new NotificationCompat.Builder(context, CHANNEL_ID_DAILY)
                .setContentTitle(title)
                .setContentText(text)
                .setSmallIcon(R.drawable.ic_notifications_black_24dp)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true);



        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            /* Create or update. */
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID_DAILY, CHANNEL_NAME_DAILY, NotificationManager.IMPORTANCE_DEFAULT);

            mBuilder.setChannelId(CHANNEL_ID_DAILY);

            if (mNotificationManager != null) {
                mNotificationManager.createNotificationChannel(channel);
            }
        }

        Notification notification = mBuilder.build();

        if (mNotificationManager != null) {
            mNotificationManager.notify(NOTIFICATION_ID_DAILY, notification);
        }
    }

    public void setRepeatingAlarm(Context context, int id_alarm,String time,String msg) {

        if (isDateInvalid(time, TIME_FORMAT)) return;

        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, AlarmReceiver.class);
        intent.putExtra(EXTRA_TYPE, id_alarm);

        String[] timeArray = time.split(":");

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, Integer.parseInt(timeArray[0]));
        calendar.set(Calendar.MINUTE, Integer.parseInt(timeArray[1]));
        calendar.set(Calendar.SECOND, 0);

        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, id_alarm, intent, 0);
        if (alarmManager != null) {
            alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent);
        }

        if (msg!=null)
            Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
    }

    public void cancelAlarm(Context context, int id_alarm, String msg) {
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, AlarmReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, id_alarm, intent, 0);
        pendingIntent.cancel();

        if (alarmManager != null)
            alarmManager.cancel(pendingIntent);

        if (msg!=null)
            Toast.makeText(context,msg, Toast.LENGTH_SHORT).show();
    }

    private boolean isDateInvalid(String date, String format) {
        try {
            DateFormat df = new SimpleDateFormat(format, Locale.getDefault());
            df.setLenient(false);
            df.parse(date);
            return false;
        } catch (ParseException e) {
            return true;
        }
    }

    public boolean isAlarmSet(Context context, int requestCode) {
        Intent intent = new Intent(context, AlarmReceiver.class);
        return PendingIntent.getBroadcast(context, requestCode, intent, PendingIntent.FLAG_NO_CREATE) != null;
    }

}
