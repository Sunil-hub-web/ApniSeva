package com.in.apniseva.AppURL;

import android.view.View;

public class ordertracking {

   /* public void bookingconfirm(){

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
                                TechnicianAllocat.setVisibility(View.INVISIBLE);
                                placed_divider1.setVisibility(View.INVISIBLE);
                                view_order_WorkInProgress.setVisibility(View.INVISIBLE);
                                placed_divider2.setVisibility(View.INVISIBLE);
                                view_order_WorkCompleted.setVisibility(View.INVISIBLE);
                                placed_divider3.setVisibility(View.INVISIBLE);
                                view_order_Payment.setVisibility(View.INVISIBLE);
                                view_order_TechnicianAllocat.setVisibility(View.INVISIBLE);
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

    public void tecnicianAllocate(){

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

    public void workInProgress(){

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

    public void workingComplete(){

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

    }*/
}
