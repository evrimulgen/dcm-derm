package in.raster.mayam.body;
 
import com.jme3.app.SimpleApplication;
import com.jme3.collision.CollisionResult;
import com.jme3.collision.CollisionResults;
import com.jme3.input.ChaseCamera;
import com.jme3.input.MouseInput;
import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.MouseButtonTrigger;
import com.jme3.light.AmbientLight;
import com.jme3.light.PointLight;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Ray;
import com.jme3.math.Vector2f;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.LightNode;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.jme3.scene.shape.Sphere;



public class BodyApp extends SimpleApplication {
    
    /**
     * TODO: Este metodo es para testeo - borrar despues
     * @param args 
     */
    public static void main(String[] args) {
        BodyApp app = new BodyApp("man.j3o");
        //AppSettings settings = new AppSettings();
        //app.setSettings(settings);
        app.setShowSettings(false);
        app.setDisplayStatView(false); 
        app.setDisplayFps(false);
        app.setPauseOnLostFocus(false);
        app.start();
    }
    
  public BodyApp(String modelPath) {
      this.modelPath = modelPath;
  }
  
  private String modelPath = null;  
  private Node shootables;
  private Node light;
  private Geometry mark;
  private ChaseCamera chaseCam;
 
  @Override
  public void simpleInitApp() {

    initKeys();
    initMark();
    viewPort.setBackgroundColor(ColorRGBA.DarkGray);
    flyCam.setEnabled(false);
    
    Spatial model = assetManager.loadModel(modelPath);
    model.scale(18.0f);
    
    AmbientLight ambient = new AmbientLight();
    ambient.setColor(ColorRGBA.White);
    rootNode.addLight(ambient);

    PointLight sun1 = new PointLight();
    sun1.setRadius(28.0f);
    rootNode.addLight(sun1);
    
    light = new LightNode("parentLight",sun1);
    rootNode.attachChild(light);
        
    shootables = new Node("Shootables");
    shootables.attachChild(model);
    rootNode.attachChild(shootables);
    
    chaseCam = new ChaseCamera(cam, shootables, inputManager);
    chaseCam.setMinVerticalRotation(-1.57f);
    chaseCam.setSmoothMotion(true);
    chaseCam.setMinDistance(10.0f);
    chaseCam.setMaxDistance(21.0f);

    inputManager.setCursorVisible(true);
}
   
  private void initKeys() {
    inputManager.addMapping("Shoot",
    new MouseButtonTrigger(MouseInput.BUTTON_RIGHT));
    inputManager.addListener(actionListener, "Shoot");
  }
  
  private ActionListener actionListener = new ActionListener() {
    @Override
    public void onAction(String name, boolean keyPressed, float tpf) {
        if (name.equals("Shoot") && !keyPressed) {
            CollisionResults results = new CollisionResults();
            Vector2f click2d = inputManager.getCursorPosition();
            Vector3f click3d = cam.getWorldCoordinates(new Vector2f(click2d.x, click2d.y), 0f).clone();
            Vector3f dir = cam.getWorldCoordinates(new Vector2f(click2d.x, click2d.y), 1f)
                .subtractLocal(click3d).normalizeLocal();
            Ray ray = new Ray(click3d, dir);
            shootables.collideWith(ray, results);
            if (results.size() > 0) {
                CollisionResult closest = results.getClosestCollision();
                // attachMark(closest.getContactPoint());
                BodyManager.getInstance().setCoord(closest.getContactPoint()); //agrega item en jList
            } else {
                rootNode.detachChild(mark);
            }
        }
    }
  };
 
  private void initMark() {
    Sphere sphere = new Sphere(30, 30, 0.2f);
    mark = new Geometry("Mark", sphere);
    Material mark_mat = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
    mark_mat.setColor("Color", ColorRGBA.Red);
    mark.setMaterial(mark_mat);
  }
  
  private void attachMark(Vector3f pos) {
      mark.setLocalTranslation(pos);
      rootNode.attachChild(mark);
  }
  
  @Override
  public void simpleUpdate(float tpf) {
        Vector3f v = BodyManager.getInstance().getCoord();
        //System.out.println("update:"+ v);
        if (v != null && !v.equals(mark.getLocalTranslation())) {
            //System.out.println("update attach");
            attachMark(v);
        }
        else {
             if (rootNode.getChild(mark.getName()) != null) {
                 rootNode.attachChild(mark);
             }
        }
        light.setLocalTranslation(cam.getLocation());
    }
}