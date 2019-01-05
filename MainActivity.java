package kpwn.com.ipv6disabler;

import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.support.v7.app.AlertDialog.Builder;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    WifiManager w;

    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_main);
        try {
            Runtime.getRuntime().exec("su");
        } catch (Exception unused) {
            Builder builder = new Builder(this);
            builder.setTitle("Could not detect root!");
            builder.setCancelable(false);
            builder.setMessage("This app requires root acces! If you are already rooted, please grant this app root permission.");
            builder.setNegativeButton("Ok", new OnClickListener() {
                public void onClick(DialogInterface dialogInterface, int i) {
                    MainActivity.this.finish();
                }
            });
            builder.create().show();
        }
        try {
            TextView textView = (TextView) findViewById(R.id.textView);
            TextView textView2 = (TextView) findViewById(R.id.textView2);
            int ipAddress = ((WifiManager) getApplicationContext().getSystemService("wifi")).getConnectionInfo().getIpAddress();
            String format = String.format("%d.%d.%d.%d", new Object[]{Integer.valueOf(ipAddress & 255), Integer.valueOf((ipAddress >> 8) & 255), Integer.valueOf((ipAddress >> 16) & 255), Integer.valueOf((ipAddress >> 24) & 255)});
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("IPv4: ");
            stringBuilder.append(format);
            textView.setText(stringBuilder.toString());
        } catch (Exception unused2) {
        }
    }

    public void enable(View view) {
        Toast.makeText(getApplicationContext(), "Ad could not be loaded", 1);
        try {
            Runtime.getRuntime().exec("echo 1 > /proc/sys/net/ipv6/conf/wlan0/disable_ipv6");
        } catch (Exception unused) {
            this.w = (WifiManager) getApplicationContext().getSystemService("wifi");
            if (this.w.isWifiEnabled()) {
                this.w.setWifiEnabled(false);
                this.w.setWifiEnabled(true);
                Toast.makeText(getApplicationContext(), "IPv6 is enabled", 0).show();
                if (!this.w.isWifiEnabled()) {
                    this.w.setWifiEnabled(true);
                }
            }
        }
    }

    public void disable(View view) {
        try {
            Runtime.getRuntime().exec("echo 0 > /proc/sys/net/ipv6/conf/wlan0/disable_ipv6");
        } catch (Exception unused) {
            this.w = (WifiManager) getApplicationContext().getSystemService("wifi");
            if (this.w.isWifiEnabled()) {
                this.w.setWifiEnabled(false);
                this.w.setWifiEnabled(true);
                Toast.makeText(this, "IPv6 is disabled", 0).show();
            }
        }
    }
}
