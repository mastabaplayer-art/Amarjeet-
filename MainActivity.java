package com.amar.headshotpanel;

import android.app.Activity;
import android.os.Bundle;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.GradientDrawable;
import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.widget.*;

public class MainActivity extends Activity {
    private LinearLayout content;
    private android.content.SharedPreferences prefs;
    private final int bg = Color.rgb(12, 17, 30), panel = Color.rgb(25, 34, 54);
    private final int accent = Color.rgb(74, 222, 128), white = Color.WHITE, muted = Color.rgb(177, 190, 211);

    @Override public void onCreate(Bundle b) {
        super.onCreate(b);
        getWindow().setStatusBarColor(bg); getWindow().setNavigationBarColor(bg);
        prefs = getSharedPreferences("amar_settings", MODE_PRIVATE);
        LinearLayout page = new LinearLayout(this); page.setOrientation(1); page.setPadding(dp(18),dp(18),dp(18),dp(24)); page.setBackgroundColor(bg);
        TextView brand = text("AMAR HEADSHOT PANEL PRO", 21, accent, true);
        page.addView(brand);
        page.addView(text("REDMI NOTE 12 5G • FREE FIRE HELPER", 11, muted, true));
        page.addView(gap(14));
        TextView notice = text("SAFE GAMING TOOL  •  No aimbot, game injection or anti-ban bypass", 12, white, false);
        notice.setPadding(dp(12),dp(12),dp(12),dp(12)); notice.setBackground(round(panel,12)); page.addView(notice);
        HorizontalScrollView tabs = new HorizontalScrollView(this); tabs.setHorizontalScrollBarEnabled(false);
        LinearLayout tabRow = new LinearLayout(this); tabRow.setOrientation(0);
        String[] names = {"Dashboard","Sensitivity","HUD","Headset","Optimize","Training"};
        for (String name:names) { Button bt=button(name); bt.setOnClickListener(v -> show(name)); tabRow.addView(bt); }
        tabs.addView(tabRow); page.addView(tabs);
        ScrollView scroll = new ScrollView(this); content = new LinearLayout(this); content.setOrientation(1); content.setPadding(0,dp(8),0,0);
        scroll.addView(content); page.addView(scroll,new LinearLayout.LayoutParams(-1,0,1));
        setContentView(page); show("Dashboard");
    }

    private void show(String section) {
        content.removeAllViews();
        if(section.equals("Dashboard")) {
            heading("Welcome, gamer");
            card("Your device","Redmi Note 12 5G\nUse Smooth graphics and close heavy background apps before playing.");
            card("Quick tools","Sensitivity • HUD • Headset • Performance • Training");
            card("Important","No third-party panel can guarantee an unbanned account. This app does not modify Free Fire.");
            action("Open Sensitivity", () -> show("Sensitivity"));
            action("Open Device Optimization", () -> show("Optimize"));
        } else if(section.equals("Sensitivity")) {
            heading("Sensitivity calculator");
            addSeek("General",98); addSeek("Red Dot",95); addSeek("2x Scope",90); addSeek("4x Scope",85); addSeek("Sniper Scope",50); addSeek("Free Look",75);
            action("Save my settings", () -> Toast.makeText(this,"Settings saved on this phone",Toast.LENGTH_SHORT).show());
            card("Tuning tip","Start with these suggested values, then adjust by 2–5 points after testing in Training. Sensitivity is personal; these are not guaranteed headshots.");
        } else if(section.equals("HUD")) {
            heading("Custom HUD guide");
            addSeek("Fire button size (%)",48);
            card("2-finger layout","Keep fire button on the right within comfortable thumb reach. Place scope and jump nearby without overlapping.");
            card("3-finger layout","Try moving fire to the upper-left for your index finger; keep movement on the left and aim controls on the right.");
            card("Aim precision","Use the in-game Default setting first. Change only after practice.");
        } else if(section.equals("Headset")) {
            heading("Headset & audio");
            card("Headset mode","Connect wired earphones or Bluetooth headphones before launching the game. Set media volume to a comfortable level.");
            card("Footstep awareness","Use the game's audio mix and keep music volume lower than effects if the game provides separate controls.");
            card("Direction practice","In Training, rotate slowly and identify left/right audio cues. Avoid excessive volume to protect hearing.");
            card("Device limitation","This helper cannot detect headset insertion or change Free Fire audio settings automatically.");
        } else if(section.equals("Optimize")) {
            heading("Redmi Note 12 5G optimization");
            card("Before playing","Restart the phone if it has been running for days. Close unused apps and pause large downloads.");
            card("Display & battery","Use a stable refresh-rate setting supported by your device. Turn Battery Saver off while gaming if performance is reduced.");
            card("Game settings","Start with Smooth graphics. Enable the highest stable in-game frame rate available; lower it if the phone overheats.");
            card("Heat & storage","Avoid playing while charging if the phone gets hot. Keep free storage available and install system updates from Settings.");
            card("Avoid risky tools","Do not use GFX injectors, modified APKs, scripts, auto-clickers or aimbots. They can compromise the account or device.");
        } else if(section.equals("Training")) {
            heading("Headshot practice routine");
            card("1 • Warm up","Spend 3 minutes tracking stationary targets with controlled movement.");
            card("2 • Drag practice","Aim around upper chest level and make a short, controlled upward swipe while firing. Adjust swipe length to your screen.");
            card("3 • Burst control","Use short bursts at medium range; reset aim between bursts instead of holding fire continuously.");
            card("4 • Review","Practice for 10 minutes, change only one sensitivity value at a time, and keep the value that feels consistent.");
        }
    }

    private void addSeek(String label, int def) {
        LinearLayout box = new LinearLayout(this); box.setOrientation(1); box.setPadding(dp(12),dp(9),dp(12),dp(9)); box.setBackground(round(panel,12));
        TextView title = text("",14,white,true); int saved=prefs.getInt(label,def); title.setText(label+"  •  "+saved);
        SeekBar seek = new SeekBar(this); seek.setMax(100); seek.setProgress(saved); seek.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener(){
            public void onProgressChanged(SeekBar s,int p,boolean u){ title.setText(label+"  •  "+p); if(u)prefs.edit().putInt(label,p).apply(); }
            public void onStartTrackingTouch(SeekBar s){} public void onStopTrackingTouch(SeekBar s){}
        });
        box.addView(title); box.addView(seek); LinearLayout.LayoutParams lp=new LinearLayout.LayoutParams(-1,-2); lp.bottomMargin=dp(9); content.addView(box,lp);
    }
    private void heading(String s){ content.addView(text(s,20,white,true)); content.addView(gap(10)); }
    private void card(String title,String body){
        LinearLayout c=new LinearLayout(this); c.setOrientation(1); c.setPadding(dp(14),dp(12),dp(14),dp(12)); c.setBackground(round(panel,14));
        c.addView(text(title,15,accent,true)); c.addView(text(body,13,muted,false));
        LinearLayout.LayoutParams lp=new LinearLayout.LayoutParams(-1,-2); lp.bottomMargin=dp(10); content.addView(c,lp);
    }
    private void action(String label,Runnable r){ Button b=button(label); b.setOnClickListener(v->r.run()); content.addView(b); }
    private Button button(String s){ Button b=new Button(this); b.setText(s); b.setTextColor(white); b.setTextSize(12); b.setAllCaps(false); b.setBackground(round(Color.rgb(38,75,65),10)); return b; }
    private TextView text(String s,int size,int color,boolean bold){ TextView t=new TextView(this); t.setText(s); t.setTextSize(size); t.setTextColor(color); if(bold)t.setTypeface(Typeface.DEFAULT,Typeface.BOLD); t.setPadding(0,dp(4),0,dp(4)); return t; }
    private View gap(int h){ View v=new View(this); v.setLayoutParams(new LinearLayout.LayoutParams(1,dp(h))); return v; }
    private GradientDrawable round(int color,int radius){ GradientDrawable d=new GradientDrawable(); d.setColor(color); d.setCornerRadius(dp(radius)); return d; }
    private int dp(int n){return (int)(n*getResources().getDisplayMetrics().density+0.5f);}
}
