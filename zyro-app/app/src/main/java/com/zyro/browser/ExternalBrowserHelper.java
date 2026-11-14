package com.zyro.browser;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import java.util.List;

public class ExternalBrowserHelper {
    public static void openInExternalBrowser(Activity activity, String url) {
        if (activity == null || url == null || url.trim().isEmpty()) {
            return;
        }
        try {
            Uri uri = Uri.parse(url.trim());
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            intent.addCategory(Intent.CATEGORY_BROWSABLE);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

            PackageManager pm = activity.getPackageManager();
            List<ResolveInfo> activities = pm.queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY);
            String extPackage = null;
            String myPkg = activity.getPackageName();

            if (activities != null) {
                for (ResolveInfo info : activities) {
                    if (info != null && info.activityInfo != null && info.activityInfo.packageName != null) {
                        String pkg = info.activityInfo.packageName;
                        if (!pkg.equalsIgnoreCase(myPkg)) {
                            extPackage = pkg;
                            break;
                        }
                    }
                }
            }

            if (extPackage == null) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://google.com"));
                List<ResolveInfo> allBrowsers = pm.queryIntentActivities(browserIntent, 0);
                if (allBrowsers != null) {
                    for (ResolveInfo info : allBrowsers) {
                        if (info != null && info.activityInfo != null && info.activityInfo.packageName != null) {
                            String pkg = info.activityInfo.packageName;
                            if (!pkg.equalsIgnoreCase(myPkg)) {
                                extPackage = pkg;
                                break;
                            }
                        }
                    }
                }
            }

            if (extPackage != null) {
                intent.setPackage(extPackage);
            }

            activity.startActivity(intent);
            activity.moveTaskToBack(true);
        } catch (Exception e) {
            try {
                Intent fallback = new Intent(Intent.ACTION_VIEW, Uri.parse(url.trim()));
                fallback.addCategory(Intent.CATEGORY_BROWSABLE);
                fallback.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                activity.startActivity(fallback);
                activity.moveTaskToBack(true);
            } catch (Exception ignored) {
            }
        }
    }
}
