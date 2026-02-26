/**
 * Open Module Declaration
 *
 * This demonstrates the 'open module' syntax which opens the ENTIRE module
 * for deep reflection at runtime.
 *
 * OPEN MODULE vs OPENS:
 *
 * 'open module com.jpms.open':
 *   - Opens ALL packages in the module for reflection
 *   - You cannot use 'opens' statements inside an open module
 *   - Equivalent to having 'opens' for every package
 *
 * 'opens com.jpms.some.package':
 *   - Opens only SPECIFIC packages for reflection
 *   - Provides more fine-grained control
 *
 * Use 'open module' when:
 *   - Your module is heavily used by reflection-based frameworks
 *   - You want to simplify module descriptors for library modules
 *   - All your classes need to be reflectively accessible (e.g., entity modules)
 *
 * Use 'opens' when:
 *   - You want to restrict which packages can be reflected upon
 *   - You have sensitive packages that should not be reflectively accessible
 */
open module com.jpms.open {

    // Export packages for compile-time access
    // Even in an open module, you still need 'exports' for compile-time visibility
    exports com.jpms.open;

    // Note: We CANNOT use 'opens' statements here because this is already
    // an open module - all packages are implicitly open for reflection.
    // Uncommenting the following would cause a compilation error:
    // opens com.jpms.open;  // ERROR: 'opens' not allowed in open modules
}

