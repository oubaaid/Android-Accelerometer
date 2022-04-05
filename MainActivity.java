package sensors.graph.cpiekarski.com.graphsensors;
// Choisir le package source
// importation des packages necessaires dans ce projet
import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Environment;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import android.os.Bundle;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;
import com.jjoe64.graphview.LegendRenderer;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.util.Log;
import android.graphics.Color;
import android.view.View;
import android.widget.Toast;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;

// MainActivity va heriter tout ses variables et fonctions à partir de la classe "Activity"
// "Activity" est une classe qui fait partie des packages disponibles sur Android.studio
// on va aussi utiliser l'interface SensorEventListener
// declaration des variables
public class MainActivity extends Activity implements SensorEventListener {
    private static final int PERMISSION_REQUEST_CODE = 100;
    private final String TAG = "GraphSensors";
    private SensorManager mSensorManager;
    private Sensor mSensor;
    private GraphView mGraphAccel;
    private GraphView mGraphAccelX;
    private GraphView mGraphAccelY;
    private GraphView mGraphAccelZ;
    Date startRecordingTime = new Date();
    Date stopRecordingTime = new Date();
    // on initialise le programme avec ce parametre "false", des qu'on commence ça devient "true"
    boolean isrecording= false;
    ArrayList<Float> xArray = new ArrayList<Float>();
    ArrayList<Float> yArray = new ArrayList<Float>();
    ArrayList<Float> zArray =  new ArrayList<Float>();
    ArrayList<Float> nArray =  new ArrayList<Float>();
    float xVal,yVal,zVal,nVal;
    private static final String fileName = "output.txt";
    private LineGraphSeries<DataPoint> mSeriesAccelX, mSeriesAccelY, mSeriesAccelZ;

    private double graphLastGyroXValue = 5d;
    private double graphLastAccelXValue = 5d;

    // on utilsera "override" et "super", pour appeler une fonction dans la classe parent "Activity"
    @Override
    //on demarre notre activité avec "onCreate", et super pour appeler le constructeur de la classe parent
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //on utilise setcontentview pour definir le XML
        setContentView(R.layout.activity_main);

        //TODO make this onCreate method not suck and subclass graphs and series
        mGraphAccel = initGraph(R.id.graphAccel, "Sensor.TYPE_ACCELEROMETER");
        mGraphAccelX = initGraph(R.id.graphAccelX, "Sensor.TYPE_ACCELEROMETER");
        mGraphAccelY = initGraph(R.id.graphAccelY, "Sensor.TYPE_ACCELEROMETER");
        mGraphAccelZ = initGraph(R.id.graphAccelZ, "Sensor.TYPE_ACCELEROMETER");


        // ACCEL
        // on donne les couleurs à nos courbes
        mSeriesAccelX = initSeries(Color.BLUE, "X");
        mSeriesAccelY = initSeries(Color.RED, "Y");
        mSeriesAccelZ = initSeries(Color.GREEN, "Z");

        // lier les graphs vides déja créer avec leurs series
        mGraphAccel.addSeries(mSeriesAccelX);
        mGraphAccel.addSeries(mSeriesAccelY);
        mGraphAccel.addSeries(mSeriesAccelZ);

        mGraphAccelX.addSeries(mSeriesAccelX);

        mGraphAccelY.addSeries(mSeriesAccelY);

        mGraphAccelZ.addSeries(mSeriesAccelZ);

        //startAccel();
        // On a finalement creer une fonction qui appelle l'accelerometre on initilisant ses graphs.


    }
    // arreter l'enregistrement à une date précise
    public void stopAccl(View view) {
        this.isrecording=false;
        stopRecordingTime = new Date();
    }
    // parametres visuels de la template des graphs
    public GraphView initGraph(int id, String title) {
        GraphView graph = (GraphView) findViewById(id);
        graph.getViewport().setXAxisBoundsManual(true);
        graph.getViewport().setMinX(0);
        graph.getViewport().setMaxX(5);
        graph.getGridLabelRenderer().setLabelVerticalWidth(100);
        graph.setTitle(title);
        graph.getGridLabelRenderer().setHorizontalLabelsVisible(true);
        graph.getLegendRenderer().setVisible(true);
        graph.getLegendRenderer().setAlign(LegendRenderer.LegendAlign.TOP);
        return graph;
    }
    // les lignes des series, on ajuste alors leurs parametres.
    // plot les données sur des points
    public LineGraphSeries<DataPoint> initSeries(int color, String title) {
        LineGraphSeries<DataPoint> series;
        series = new LineGraphSeries<>();
        series.setDrawDataPoints(true);
        series.setDrawBackground(false);
        series.setColor(color);
        series.setTitle(title);
        return series;
    }


    // on commence l'enregistrement, on stock les données dans un tableau
    // on initialie les series avec la fonction "initSeries"
    public void startAccel(View view){
        startRecordingTime = new Date();
        this.isrecording=true;
        this.xArray = new ArrayList<Float>();
        this.zArray = new ArrayList<Float>();
        this.yArray = new ArrayList<Float>();
        this.nArray = new ArrayList<Float>();
        mSeriesAccelX = initSeries(Color.BLUE, "X");
        mSeriesAccelY = initSeries(Color.RED, "Y");
        mSeriesAccelZ = initSeries(Color.GREEN, "Z");

        //on efface les données déja visualisées durant la session precedante.
        mGraphAccel.removeAllSeries();
        mGraphAccelY.removeAllSeries();
        mGraphAccelX.removeAllSeries();
        mGraphAccelZ.removeAllSeries();

        // on rempli les graphes avec les nouvelles données acquises.
        mGraphAccel.addSeries(mSeriesAccelX);
        mGraphAccel.addSeries(mSeriesAccelY);
        mGraphAccel.addSeries(mSeriesAccelZ);

        mGraphAccelX.addSeries(mSeriesAccelX);

        mGraphAccelY.addSeries(mSeriesAccelY);

        mGraphAccelZ.addSeries(mSeriesAccelZ);

        // saisir la source des données acquises, recepteure accelerometre.
        mSensorManager = (SensorManager) this.getSystemService(Context.SENSOR_SERVICE);
        mSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        mSensorManager.registerListener(this, mSensor, SensorManager.SENSOR_DELAY_NORMAL);
    }


    @Override
    public void onSensorChanged(SensorEvent event) {
        //event.values[x,y,z]
        //remplir notre tableau avec les valeurs acquises du capteur
        if(event.sensor.getType() == Sensor.TYPE_ACCELEROMETER && isrecording == true) {
            this.xArray.add(event.values[0]);
            this.yArray.add(event.values[1]);
            this.zArray.add(event.values[2]);
            // calculer la valeur acceleration absolue.
            this.nArray.add((float) Math.sqrt(Math.pow(event.values[0],2)+Math.pow(event.values[1],2)+Math.pow(event.values[2],2)));
            // on ajoute à partir de l'emplacement suivant les valeurs de X, Y et Z.
            graphLastAccelXValue += 0.15d;
            mSeriesAccelX.appendData(new DataPoint(graphLastAccelXValue, event.values[0]), true, 100);
            mSeriesAccelY.appendData(new DataPoint(graphLastAccelXValue, event.values[1]), true, 100);
            mSeriesAccelZ.appendData(new DataPoint(graphLastAccelXValue, event.values[2]), true, 100);
        }

        //      String dataString = String.valueOf(event.accuracy) + "," + String.valueOf(event.timestamp)
        //      + "," + String.valueOf(event.sensor.getType()) + "\n";
        //    Log.d(TAG, "Data received: " + dataString);
    }
    ////////////////////
    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        Log.d(TAG, "onAccuracyChanged called for the gyro");
    }
    ///////////////////
// Fonction d'enregistrement des données Accelration Absolue
    public void saveRecordedData(View view) throws IOException {
        String status = Environment.getExternalStorageState();
        int period = 100;
        int j=0;
        if (Environment.MEDIA_MOUNTED.equals(status)) {
            if (Build.VERSION.SDK_INT >= 23) {
                if (checkPermission()) {
                    File sdcard =Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
                    File dirFile = new File(sdcard.getAbsolutePath() + "/data/");
                    dirFile.mkdir();
                    File csvFile = new File(dirFile, "Signal.csv");
                    try (PrintWriter csvWriter = new PrintWriter(new FileWriter(csvFile));){
                        csvWriter.println("Acceleration(m/s^2),Time(10e-1.s),,Start Date,End Date");
                        for(Float item : nArray){
                            if(j==0){
                                csvWriter.println(item+","+period*j+",,"+startRecordingTime.toString()+","+stopRecordingTime.toString());

                            }else{
                                csvWriter.println(item+","+period*j);
                            }
                            j++;
                        }
                        Toast.makeText(this, "File has been Saved into " + sdcard.getAbsolutePath() + "/data/Signal.csv file", Toast.LENGTH_LONG).show();
                    } catch (IOException exception) {
                        exception.printStackTrace();
                    }
                } else {
                    requestPermission();
                }
            } else {
                File sdcard = Environment.getExternalStoragePublicDirectory(
                        Environment.DIRECTORY_DOWNLOADS);
                File dir = new File(sdcard.getAbsolutePath() + "/data/");
                dir.mkdir();
                File csvFile = new File(dir, "Signal.csv");
                try (PrintWriter csvWriter = new PrintWriter(new FileWriter(csvFile));){
                    csvWriter.println("Acceleration Norm(m/s^2),Time(10e-1.s),,Start Date,End Date");
                    for(Float item : nArray){
                        if(j==0){
                            csvWriter.println(item+","+period*j+",,"+startRecordingTime.toString()+","+stopRecordingTime.toString());

                        }else{
                            csvWriter.println(item+","+period*j);
                        }
                        j++;
                    }
                    Toast.makeText(this, "File has been Saved into " + sdcard.getAbsolutePath() + "/data/Signal.csv file", Toast.LENGTH_LONG).show();
                } catch (IOException exception) {
                    exception.printStackTrace();
                }
            }
        }
    }
    // verification de la permission de l'enregistrement
    private boolean checkPermission() {
        int result = ContextCompat.checkSelfPermission(MainActivity.this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (result == PackageManager.PERMISSION_GRANTED) {
            return true;
        } else {
            return false;
        }
    }
    //initialisation  et application de la fonction permission d'enregistrement
    private void requestPermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            Toast.makeText(MainActivity.this, "Write External Storage permission allows us to create files. Please allow this permission in App Settings.", Toast.LENGTH_LONG).show();
        } else {
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE}, PERMISSION_REQUEST_CODE);
        }
    }
    // resultat et visualisation de la demande de permission et d'enregistrement
    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case PERMISSION_REQUEST_CODE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Log.e("value", "Permission Granted, Now you can use local drive .");
                } else {
                    Log.e("value", "Permission Denied, You cannot use local drive .");
                }
                break;
        }
    }


}
