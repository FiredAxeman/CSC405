public class CT6Disc {

    // A simple class to handle 3D Vectors (x, y, z)
    static class Vector3D {
        double x, y, z;

        public Vector3D(double x, double y, double z) {
            this.x = x;
            this.y = y;
            this.z = z;
        }

        // Calculates the Dot Product (how aligned two vectors are)
        public double dotProduct(Vector3D other) {
            return (this.x * other.x) + (this.y * other.y) + (this.z * other.z);
        }

        // Multiplies the vector by a scalar value
        public Vector3D multiply(double scalar) {
            return new Vector3D(this.x * scalar, this.y * scalar, this.z * scalar);
        }

        // Subtracts another vector from this one
        public Vector3D subtract(Vector3D other) {
            return new Vector3D(this.x - other.x, this.y - other.y, this.z - other.z);
        }
        
        // Normalizes the vector (makes its length exactly 1.0)
        public Vector3D normalize() {
            double length = Math.sqrt(x*x + y*y + z*z);
            return new Vector3D(x/length, y/length, z/length);
        }
        
        @Override
        public String toString() {
            return String.format("(%.2f, %.2f, %.2f)", x, y, z);
        }
    }

    // Formula: R = I - 2 * (I dot N) * N
    public static Vector3D calculateReflection(Vector3D incident, Vector3D normal) {
        double dotProduct = incident.dotProduct(normal);
        Vector3D scaledNormal = normal.multiply(2 * dotProduct);
        return incident.subtract(scaledNormal).normalize();
    }

    // Calculates the intensity of the light reaching the eye (Phong Model)
    public static double calculateSpecularIntensity(Vector3D reflection, Vector3D viewVector, double shininess) {
        double alignment = Math.max(0.0, reflection.dotProduct(viewVector));
        return Math.pow(alignment, shininess);
    }

    public static void main(String[] args) {
        System.out.println("--- 3D Lighting Reflection Simulation ---");
        Vector3D lightDir = new Vector3D(0.5, -1.0, 0.0).normalize(); 
        Vector3D surfaceNormal = new Vector3D(0.0, 1.0, 0.0).normalize(); 
        Vector3D viewDir = new Vector3D(0.5, 1.0, 0.0).normalize(); 
        Vector3D reflectionVector = calculateReflection(lightDir, surfaceNormal);
        System.out.println("Light Incident Vector: " + lightDir);
        System.out.println("Surface Normal Vector: " + surfaceNormal);
        System.out.println("Calculated Reflection Vector: " + reflectionVector);
        double plasticShininess = 32.0;
        double lightIntensity = calculateSpecularIntensity(reflectionVector, viewDir, plasticShininess);

        System.out.printf("\nSpecular Highlight Intensity (0.0 to 1.0): %.4f\n", lightIntensity);
        if (lightIntensity > 0.8) {
            System.out.println("Result: The camera sees a very bright highlight. The light is shining directly into the lens!");
        } else if (lightIntensity > 0.1) {
            System.out.println("Result: The camera sees a soft glimmer of light.");
        } else {
            System.out.println("Result: No highlight visible. The reflected light is traveling away from the camera.");
        }
    }
}