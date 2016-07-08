package main.blockynights;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

public class main extends JavaPlugin implements Listener {

	   static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";  
	   static final String DB_URL = "jdbc:mysql://localhost/zperm";
	   static final String USER = "user";
	   static final String PASS = "password";
	  
	   @Override
		public void onEnable() {
		}

		@Override
		public void onDisable() {
		}

		public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
			
			if ((cmd.getName().equalsIgnoreCase("played") && sender instanceof Player) && (args.length == 0)) {
				Player p = (Player) sender;
				String user = p.getUniqueId().toString();
				Connection conn = null;
				Statement stmt = null;
				   try{
					      Class.forName("com.mysql.jdbc.Driver");
					      conn = DriverManager.getConnection(DB_URL,USER,PASS);
					      stmt = conn.createStatement();
					      String sql;
					      sql = "SELECT create_date FROM users where uiid='" + user + "'";
					      ResultSet rs = stmt.executeQuery(sql);
					      if (rs.next()) {
					    	  Date createdate = new Date ();
					    	  createdate.setTime((long)rs.getInt("create_date")*1000);
					    	  Date currentDate = new Date ();
					    	  currentDate .setTime((long)(System.currentTimeMillis() / 1000L)*1000);
					    	  long diffInDays = (long)(currentDate.getTime() - createdate.getTime()) / (1000 * 60 * 60 * 24) +1;
					    	  p.sendMessage(ChatColor.AQUA + "You have played for: " + ChatColor.GOLD + diffInDays + ChatColor.AQUA + " Days.");
					    	  p.sendRawMessage(ChatColor.AQUA +"You joined the server on: " + ChatColor.GOLD + createdate);
					      } else { p.sendMessage("Database connection lost or you are not in it? Try relogging."); }
					      rs.close();stmt.close();conn.close();
				   }catch(SQLException se){se.printStackTrace();}catch(Exception e){e.printStackTrace();}
			   		finally{try{if(stmt!=null)stmt.close();}catch(SQLException se2){}try{if(conn!=null)conn.close();}catch(SQLException se){se.printStackTrace();}}
				
				return true;
			}
			if ((cmd.getName().equalsIgnoreCase("played") && sender instanceof Player) && (args.length == 1)) {
				Player SendUser = (Player) sender;
				String user = args[0];
				Connection conn = null;
				Statement stmt = null;
				   try{
					      Class.forName("com.mysql.jdbc.Driver");
					      conn = DriverManager.getConnection(DB_URL,USER,PASS);
					      stmt = conn.createStatement();
					      String sql;
					      sql = "SELECT create_date FROM users where latest_name='" + user + "'";
					      ResultSet rs = stmt.executeQuery(sql);
					      if (rs.next()) {
					    	  Date createdate = new Date ();
					    	  createdate.setTime((long)rs.getInt("create_date")*1000);
					    	  Date currentDate = new Date ();
					    	  currentDate .setTime((long)(System.currentTimeMillis() / 1000L)*1000);
					    	  long diffInDays = (long)(currentDate.getTime() - createdate.getTime()) / (1000 * 60 * 60 * 24) +1;
					    	  SendUser.sendMessage(ChatColor.AQUA + args[0] + " have played for: " + ChatColor.GOLD + diffInDays + ChatColor.AQUA + " Days.");
					    	  SendUser.sendRawMessage(ChatColor.AQUA + args[0] + " joined the server on: " + ChatColor.GOLD + createdate);
					      } else { SendUser.sendMessage(ChatColor.AQUA +"Did you type the username correctly? we couldnt find a match."); }
					      rs.close();stmt.close();conn.close();
				   }catch(SQLException se){se.printStackTrace();}catch(Exception e){e.printStackTrace();}
			   		finally{try{if(stmt!=null)stmt.close();}catch(SQLException se2){}try{if(conn!=null)conn.close();}catch(SQLException se){se.printStackTrace();}}
				
				return true;
			}
			return false;
		}

}
