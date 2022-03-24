package com.in.apniseva;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import com.in.apniseva.R;

public class OrderTracking extends AppCompatActivity {

    View view_order_BookingConfirm, view_order_TechnicianAllocat, view_order_WorkInProgress, view_order_WorkCompleted,
            view_order_Payment;
    RelativeLayout BookingConfirm, TechnicianAllocat;
    LinearLayout WorkInProgress, WorkCompleted, Payment;
    ProgressBar placed_divider1, placed_divider, placed_divider2, placed_divider3;
    private int i = 0;
    private Handler hdlr = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_tracking);

        Payment = findViewById(R.id.Payment);
        BookingConfirm = findViewById(R.id.BookingConfirm);
        WorkInProgress = findViewById(R.id.WorkInProgress);
        WorkCompleted = findViewById(R.id.WorkCompleted);
        TechnicianAllocat = findViewById(R.id.TechnicianAllocat);
        placed_divider1 = findViewById(R.id.placed_divider1);
        view_order_WorkInProgress = findViewById(R.id.view_order_WorkInProgress);
        placed_divider2 = findViewById(R.id.placed_divider2);
        view_order_WorkCompleted = findViewById(R.id.view_order_WorkCompleted);
        placed_divider3 = findViewById(R.id.placed_divider3);
        view_order_Payment = findViewById(R.id.view_order_Payment);
        view_order_TechnicianAllocat = findViewById(R.id.view_order_TechnicianAllocat);
        placed_divider = findViewById(R.id.placed_divider);
        view_order_BookingConfirm = findViewById(R.id.view_order_BookingConfirm);

        Payment.setVisibility(View.INVISIBLE);
        WorkInProgress.setVisibility(View.INVISIBLE);
        WorkCompleted.setVisibility(View.INVISIBLE);
        TechnicianAllocat.setVisibility(View.INVISIBLE);
        placed_divider1.setVisibility(View.INVISIBLE);
        view_order_WorkInProgress.setVisibility(View.INVISIBLE);
        placed_divider2.setVisibility(View.INVISIBLE);
        view_order_WorkCompleted.setVisibility(View.INVISIBLE);
        placed_divider3.setVisibility(View.INVISIBLE);
        view_order_Payment.setVisibility(View.INVISIBLE);
        view_order_TechnicianAllocat.setVisibility(View.INVISIBLE);
        placed_divider.setVisibility(View.VISIBLE);

        placed_divider.setProgress(20);
        placed_divider1.setProgress(20);
        placed_divider2.setProgress(20);
        placed_divider3.setProgress(20);

        BookingConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                i = placed_divider.getProgress();
                new Thread(new Runnable() {
                    public void run() {
                        while (i < 100) {
                            i += 5;
                            // Update the progress bar and display the current value in text view
                            hdlr.post(new Runnable() {
                                public void run() {

                                    placed_divider.setProgress(i);

                                    if (i == 100) {

                                        Payment.setVisibility(View.INVISIBLE);
                                        WorkInProgress.setVisibility(View.INVISIBLE);
                                        WorkCompleted.setVisibility(View.INVISIBLE);
                                        TechnicianAllocat.setVisibility(View.VISIBLE);
                                        placed_divider1.setVisibility(View.VISIBLE);
                                        view_order_WorkInProgress.setVisibility(View.INVISIBLE);
                                        placed_divider2.setVisibility(View.INVISIBLE);
                                        view_order_WorkCompleted.setVisibility(View.INVISIBLE);
                                        placed_divider3.setVisibility(View.INVISIBLE);
                                        view_order_Payment.setVisibility(View.INVISIBLE);
                                        view_order_TechnicianAllocat.setVisibility(View.VISIBLE);
                                        placed_divider.setVisibility(View.VISIBLE);

                                        placed_divider.setMax(100); // 100 maximum value for the progress value
                                        placed_divider1.setProgress(20); // 50 default progress value for the progress bar

                                    }
                                }
                            });
                            try {
                                // Sleep for 100 milliseconds to show the progress slowly.
                                Thread.sleep(100);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }).start();

            }
        });

        TechnicianAllocat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                i = placed_divider1.getProgress();
                new Thread(new Runnable() {
                    public void run() {
                        while (i < 100) {
                            i += 5;
                            // Update the progress bar and display the current value in text view
                            hdlr.post(new Runnable() {
                                public void run() {

                                    placed_divider1.setProgress(i);

                                    if (i == 100) {

                                        Payment.setVisibility(View.INVISIBLE);
                                        WorkInProgress.setVisibility(View.VISIBLE);
                                        WorkCompleted.setVisibility(View.INVISIBLE);
                                        TechnicianAllocat.setVisibility(View.VISIBLE);
                                        placed_divider1.setVisibility(View.VISIBLE);
                                        view_order_WorkInProgress.setVisibility(View.VISIBLE);
                                        placed_divider2.setVisibility(View.VISIBLE);
                                        view_order_WorkCompleted.setVisibility(View.INVISIBLE);
                                        placed_divider3.setVisibility(View.INVISIBLE);
                                        view_order_Payment.setVisibility(View.INVISIBLE);
                                        view_order_TechnicianAllocat.setVisibility(View.VISIBLE);
                                        placed_divider.setVisibility(View.VISIBLE);

                                        placed_divider.setMax(100); // 100 maximum value for the progress value
                                        placed_divider.setProgress(100); // 50 default progress value for the progress bar
                                        placed_divider2.setProgress(20); // 50 default progress value for the progress bar

                                    }
                                }
                            });
                            try {
                                // Sleep for 100 milliseconds to show the progress slowly.
                                Thread.sleep(100);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }).start();

            }
        });

        WorkInProgress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                i = placed_divider2.getProgress();
                new Thread(new Runnable() {
                    public void run() {
                        while (i < 100) {
                            i += 5;
                            // Update the progress bar and display the current value in text view
                            hdlr.post(new Runnable() {
                                public void run() {

                                    placed_divider2.setProgress(i);

                                    if (i == 100) {

                                        Payment.setVisibility(View.INVISIBLE);
                                        WorkInProgress.setVisibility(View.VISIBLE);
                                        WorkCompleted.setVisibility(View.VISIBLE);
                                        TechnicianAllocat.setVisibility(View.VISIBLE);
                                        placed_divider1.setVisibility(View.VISIBLE);
                                        view_order_WorkInProgress.setVisibility(View.VISIBLE);
                                        placed_divider2.setVisibility(View.VISIBLE);
                                        view_order_WorkCompleted.setVisibility(View.VISIBLE);
                                        placed_divider3.setVisibility(View.VISIBLE);
                                        view_order_Payment.setVisibility(View.INVISIBLE);
                                        view_order_TechnicianAllocat.setVisibility(View.VISIBLE);
                                        placed_divider.setVisibility(View.VISIBLE);

                                        placed_divider.setMax(100); // 100 maximum value for the progress value
                                        placed_divider.setProgress(100); // 50 default progress value for the progress bar

                                        placed_divider1.setProgress(100); // 50 default progress value for the progress bar
                                        placed_divider3.setProgress(20); // 50 default progress value for the progress bar
                                    }
                                }
                            });
                            try {
                                // Sleep for 100 milliseconds to show the progress slowly.
                                Thread.sleep(100);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }).start();




            }
        });

        WorkCompleted.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                i = placed_divider3.getProgress();
                new Thread(new Runnable() {
                    public void run() {
                        while (i < 100) {
                            i += 5;
                            // Update the progress bar and display the current value in text view
                            hdlr.post(new Runnable() {
                                public void run() {

                                    placed_divider3.setProgress(i);

                                    if (i == 100) {

                                        Payment.setVisibility(View.VISIBLE);
                                        WorkInProgress.setVisibility(View.VISIBLE);
                                        WorkCompleted.setVisibility(View.VISIBLE);
                                        TechnicianAllocat.setVisibility(View.VISIBLE);
                                        placed_divider1.setVisibility(View.VISIBLE);
                                        view_order_WorkInProgress.setVisibility(View.VISIBLE);
                                        placed_divider2.setVisibility(View.VISIBLE);
                                        view_order_WorkCompleted.setVisibility(View.VISIBLE);
                                        placed_divider3.setVisibility(View.VISIBLE);
                                        view_order_Payment.setVisibility(View.VISIBLE);
                                        view_order_TechnicianAllocat.setVisibility(View.VISIBLE);
                                        placed_divider.setVisibility(View.VISIBLE);

                                        placed_divider.setMax(100); // 100 maximum value for the progress value
                                        placed_divider.setProgress(100); // 50 default progress value for the progress bar

                                        placed_divider1.setProgress(100); // 50 default progress value for the progress bar
                                        placed_divider2.setProgress(100); // 50 default progress value for the progress bar
                                    }
                                }
                            });
                            try {
                                // Sleep for 100 milliseconds to show the progress slowly.
                                Thread.sleep(100);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }).start();




            }
        });

    }
}