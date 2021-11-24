package co.fouani.healthinfo.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import androidx.annotation.NonNull;

import java.util.Collection;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import co.fouani.healthinfo.data.models.Post;

public class Utility {
    public static String getSection(int section) {
        switch (section) {
            case 1:
                return "Diabetes Info";
            case 2:
                return "Healthy Heart";
            case 3:
                return "Veganism";
            default:
                return "Continue";
        }
    }

    public static void updatePostVisit(Post original, @NonNull Post visited) {
        if (original != null) {
            original.setProgress(visited.getProgress());
            original.setTotal(visited.getTotal());
        }
    }

    public static String getVideoId(@NonNull String url) {
        String videoId = "";
        String regex = "http(?:s)?:\\/\\/(?:m.)?(?:www\\.)?youtu(?:\\.be\\/|be\\.com\\/(?:watch\\?(?:feature=youtu.be\\&)?v=|v\\/|embed\\/|user\\/(?:[\\w#]+\\/)+))([^&#?\\n]+)";
        Pattern pattern = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(url);
        if (matcher.find()) {
            videoId = matcher.group(1);
        }
        return videoId;
    }

    public static boolean isNetworkAvailable(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = connectivityManager.getActiveNetworkInfo();
        return activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();
    }

    public static boolean exists(String v) {
        return v != null && !v.trim().isEmpty();
    }

    public static boolean exists(Collection<?> v) {
        return v != null && !v.isEmpty();
    }

}
