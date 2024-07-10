package app.persistence.repository;

// TODO 2: MODIFY THE CLASS TO IMPLEMENT THE INTERFACE
//@Component
//public class SomethingDBRepository implements ISomethingRepository {
//
//    private JdbcUtils dbUtils;
//    private static final Logger logger = LogManager.getLogger();
//
//    public SomethingDBRepository(Properties props) {
//        logger.info("Initializing SomethingDBRepository with properties: {}", props);
//        dbUtils = new JdbcUtils(props);
//    }
//
//    @Override
//    public Optional<Something> findOne(Long id) {
//        // method to find a something by id
//        logger.traceEntry();
//        Connection con = dbUtils.getConnection();
//        try (PreparedStatement preStmt = con.prepareStatement("SELECT * FROM Somethings where id=?")) {
//            preStmt.setLong(1, id);
//            try (ResultSet result = preStmt.executeQuery()) {
//                if (result.next()) {
//                    String name = result.getString("name");
//                    String password = result.getString("password");
//                    Something something = new Something(name, password);
//                    something.setId(id);
//                    return Optional.of(something);
//                }
//            }
//        } catch (SQLException e) {
//            logger.error(e);
//            System.out.println("Error Database: " + e);
//        }
//        return Optional.empty();
//    }
//
//    @Override
//    public Iterable<Something> findAll() {
//        // method to find all somethings
//        logger.traceEntry();
//        Connection con = dbUtils.getConnection();
//        List<Something> somethings = new ArrayList<>();
//        try (PreparedStatement preStmt = con.prepareStatement("SELECT * FROM Somethings")) {
//            try (ResultSet result = preStmt.executeQuery()) {
//                while (result.next()) {
//                    Long id = result.getLong("id");
//                    String name = result.getString("name");
//                    String password = result.getString("password");
//                    Something something = new Something(name, password);
//                    something.setId(id);
//                    somethings.add(something);
//                }
//            }
//        } catch (SQLException e) {
//            logger.error(e);
//            System.out.println("Error Database: " + e);
//        }
//        return somethings;
//    }
//
//    @Override
//    public Optional<Something> update(Something something) {
//        // method to update a something
//        logger.traceEntry("updating task with {}", something);
//        Connection con = dbUtils.getConnection();
//        try (PreparedStatement preStmt = con.prepareStatement("UPDATE Somethings SET name=?, password=? WHERE id=?")) {
//            preStmt.setString(1, something.getName());
//            preStmt.setString(2, something.getPassword());
//            preStmt.setLong(3, something.getId());
//            int result = preStmt.executeUpdate();
//            if (result > 0) {
//                return Optional.of(something);
//            }
//        } catch (SQLException e) {
//            logger.error(e);
//            System.out.println("Error Database: " + e);
//        }
//        return Optional.empty();
//    }
//
//    @Override
//    public Optional<Something> delete(Long id) {
//        // method to delete a something by id
//        logger.traceEntry("deleting task with {}", id);
//        Connection con = dbUtils.getConnection();
//        Optional<Something> something = findOne(id);
//        if (something.isPresent()) {
//            try (PreparedStatement preStmt = con.prepareStatement("DELETE FROM Somethings WHERE id=?")) {
//                preStmt.setLong(1, id);
//                int result = preStmt.executeUpdate();
//                if (result > 0) {
//                    return something;
//                }
//            } catch (SQLException e) {
//                logger.error(e);
//                System.out.println("Error Database: " + e);
//            }
//        }
//        return Optional.empty();
//    }
//
//    @Override
//    public Optional<Something> save(Something something) {
//        logger.traceEntry("saving task {}", something);
//        Connection connection = dbUtils.getConnection();
//        try (PreparedStatement preStmt = connection.prepareStatement("INSERT INTO Somethings (name, password) VALUES (?, ?)")) {
//            preStmt.setString(1, something.getName());
//            preStmt.setString(2, something.getPassword());
//            int result = preStmt.executeUpdate();
//            logger.trace("Saved {} instances", result);
//            return Optional.of(something);
//        } catch (SQLException ex) {
//            logger.error(ex);
//            System.out.println("Error Database: " + ex);
//        }
//        logger.traceExit();
//        return Optional.empty();
//    }
//}
