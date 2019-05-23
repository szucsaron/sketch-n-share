DROP TABLE IF EXISTS shares;
DROP TABLE IF EXISTS sketches;
DROP TABLE IF EXISTS folders;
DROP TABLE IF EXISTS users;


-- Create tables

CREATE TABLE users (
	id SERIAL PRIMARY KEY,
	name VARCHAR(100) UNIQUE,
	password VARCHAR(200),
	role NUMERIC(1)
);

CREATE TABLE folders (
	id SERIAL PRIMARY KEY,
	name VARCHAR(100),
	owner INT REFERENCES users(id)
);

CREATE TABLE sketches (
	id SERIAL PRIMARY KEY,
	name VARCHAR(100),
	folders_id INT REFERENCES folders(id),
	content TEXT
);

CREATE TABLE shares (
	users_id INT REFERENCES users(id),
	folders_id INT REFERENCES folders(id),
	PRIMARY KEY (users_id, folders_id)
);


-- Insert data

INSERT INTO users (name, password, role) VALUES 
('a', 'a', '0'), --1
('b', 'b', '0') --2
;


INSERT INTO folders (name, owner) VALUES
('Work', 1), --1
('Unfinished Projects', 1), --2
('My Sketches', 2) --2
;

INSERT INTO sketches (name, folders_id, content) VALUES 
('Animal study', 1, '[{"type":"line","id":0,"pos1":[361,387],"pos2":[419,499],"color":"black"},{"type":"line","id":1,"pos1":[420,501],"pos2":[468,541],"color":"black"},{"type":"line","id":2,"pos1":[465,540],"pos2":[506,518],"color":"black"},{"type":"line","id":3,"pos1":[506,518],"pos2":[531,383],"color":"black"},{"type":"line","id":4,"pos1":[531,383],"pos2":[505,332],"color":"black"},{"type":"line","id":5,"pos1":[505,332],"pos2":[448,315],"color":"black"},{"type":"line","id":6,"pos1":[448,315],"pos2":[396,324],"color":"black"},{"type":"line","id":7,"pos1":[397,324],"pos2":[363,389],"color":"black"},{"type":"line","id":8,"pos1":[394,334],"pos2":[360,277],"color":"black"},{"type":"line","id":9,"pos1":[328,259],"pos2":[360,279],"color":"black"},{"type":"line","id":10,"pos1":[328,260],"pos2":[289,255],"color":"black"},{"type":"line","id":11,"pos1":[288,259],"pos2":[324,273],"color":"black"},{"type":"line","id":12,"pos1":[324,277],"pos2":[352,302],"color":"black"},{"type":"line","id":13,"pos1":[355,305],"pos2":[383,352],"color":"black"},{"type":"line","id":14,"pos1":[487,328],"pos2":[505,290],"color":"black"},{"type":"line","id":15,"pos1":[506,290],"pos2":[535,258],"color":"black"},{"type":"line","id":16,"pos1":[535,258],"pos2":[587,242],"color":"black"},{"type":"line","id":17,"pos1":[587,242],"pos2":[563,265],"color":"black"},{"type":"line","id":18,"pos1":[531,285],"pos2":[521,303],"color":"black"},{"type":"line","id":19,"pos1":[523,305],"pos2":[507,338],"color":"black"},{"type":"line","id":20,"pos1":[532,290],"pos2":[567,265],"color":"black"},{"type":"line","id":21,"pos1":[370,382],"pos2":[259,352],"color":"black"},{"type":"line","id":22,"pos1":[259,352],"pos2":[232,372],"color":"black"},{"type":"line","id":23,"pos1":[233,374],"pos2":[261,405],"color":"black"},{"type":"line","id":24,"pos1":[262,405],"pos2":[369,402],"color":"black"},{"type":"line","id":25,"pos1":[519,371],"pos2":[611,302],"color":"black"},{"type":"line","id":26,"pos1":[611,302],"pos2":[640,315],"color":"black"},{"type":"line","id":27,"pos1":[640,315],"pos2":[642,349],"color":"black"},{"type":"line","id":28,"pos1":[642,349],"pos2":[532,388],"color":"black"},{"type":"line","id":29,"pos1":[384,406],"pos2":[403,399],"color":"black"},{"type":"line","id":30,"pos1":[403,399],"pos2":[413,411],"color":"black"},{"type":"line","id":31,"pos1":[413,411],"pos2":[411,411],"color":"black"},{"type":"line","id":32,"pos1":[387,408],"pos2":[394,418],"color":"black"},{"type":"line","id":33,"pos1":[394,419],"pos2":[414,411],"color":"black"},{"type":"line","id":34,"pos1":[486,400],"pos2":[503,395],"color":"black"},{"type":"line","id":35,"pos1":[503,395],"pos2":[514,404],"color":"black"},{"type":"line","id":36,"pos1":[514,404],"pos2":[505,413],"color":"black"},{"type":"line","id":37,"pos1":[505,413],"pos2":[490,401],"color":"black"},{"type":"line","id":38,"pos1":[443,507],"pos2":[462,516],"color":"black"},{"type":"line","id":39,"pos1":[462,516],"pos2":[491,506],"color":"black"},{"type":"line","id":40,"pos1":[442,477],"pos2":[459,467],"color":"black"},{"type":"line","id":41,"pos1":[460,468],"pos2":[480,470],"color":"black"},{"type":"line","id":42,"pos1":[480,470],"pos2":[486,475],"color":"black"},{"type":"line","id":43,"pos1":[487,477],"pos2":[482,491],"color":"black"},{"type":"line","id":44,"pos1":[482,491],"pos2":[464,492],"color":"black"},{"type":"line","id":45,"pos1":[463,492],"pos2":[443,480],"color":"black"},{"type":"line","id":46,"pos1":[451,479],"pos2":[466,485],"color":"black"},{"type":"line","id":47,"pos1":[466,485],"pos2":[481,482],"color":"black"},{"type":"line","id":48,"pos1":[466,487],"pos2":[464,493],"color":"black"},{"type":"line","id":49,"pos1":[523,462],"pos2":[716,475],"color":"black"},{"type":"line","id":50,"pos1":[716,475],"pos2":[792,397],"color":"black"},{"type":"line","id":51,"pos1":[792,397],"pos2":[905,392],"color":"black"},{"type":"line","id":52,"pos1":[905,392],"pos2":[981,393],"color":"black"},{"type":"line","id":53,"pos1":[981,393],"pos2":[1035,427],"color":"black"},{"type":"line","id":54,"pos1":[1036,427],"pos2":[1065,468],"color":"black"},{"type":"line","id":55,"pos1":[1065,468],"pos2":[1075,521],"color":"black"},{"type":"line","id":56,"pos1":[1075,521],"pos2":[1017,556],"color":"black"},{"type":"line","id":57,"pos1":[1017,556],"pos2":[943,579],"color":"black"},{"type":"line","id":58,"pos1":[944,580],"pos2":[824,601],"color":"black"},{"type":"line","id":59,"pos1":[824,601],"pos2":[768,589],"color":"black"},{"type":"line","id":60,"pos1":[765,588],"pos2":[738,564],"color":"black"},{"type":"line","id":61,"pos1":[737,563],"pos2":[722,536],"color":"black"},{"type":"line","id":62,"pos1":[722,536],"pos2":[718,506],"color":"black"},{"type":"line","id":63,"pos1":[718,504],"pos2":[718,480],"color":"black"},{"type":"line","id":64,"pos1":[718,480],"pos2":[764,587],"color":"black"},{"type":"line","id":65,"pos1":[763,591],"pos2":[624,723],"color":"black"},{"type":"line","id":66,"pos1":[766,593],"pos2":[841,733],"color":"black"},{"type":"line","id":67,"pos1":[1004,567],"pos2":[976,720],"color":"black"},{"type":"line","id":68,"pos1":[1009,564],"pos2":[1142,708],"color":"black"},{"type":"line","id":69,"pos1":[624,722],"pos2":[595,750],"color":"black"},{"type":"line","id":70,"pos1":[595,750],"pos2":[636,759],"color":"black"},{"type":"line","id":71,"pos1":[636,759],"pos2":[630,724],"color":"black"},{"type":"line","id":72,"pos1":[629,724],"pos2":[619,753],"color":"black"},{"type":"line","id":73,"pos1":[839,733],"pos2":[808,761],"color":"black"},{"type":"line","id":74,"pos1":[809,761],"pos2":[843,762],"color":"black"},{"type":"line","id":75,"pos1":[843,762],"pos2":[842,734],"color":"black"},{"type":"line","id":76,"pos1":[841,734],"pos2":[826,761],"color":"black"},{"type":"line","id":77,"pos1":[977,721],"pos2":[947,754],"color":"black"},{"type":"line","id":78,"pos1":[947,754],"pos2":[992,754],"color":"black"},{"type":"line","id":79,"pos1":[992,754],"pos2":[977,725],"color":"black"},{"type":"line","id":80,"pos1":[977,725],"pos2":[971,756],"color":"black"},{"type":"line","id":81,"pos1":[1142,712],"pos2":[1126,742],"color":"black"},{"type":"line","id":82,"pos1":[1126,742],"pos2":[1161,742],"color":"black"},{"type":"line","id":83,"pos1":[1161,742],"pos2":[1145,711],"color":"black"},{"type":"line","id":84,"pos1":[1144,714],"pos2":[1146,744],"color":"black"}]'
), -- 1
('Flowers', 1, '[{"type":"line","id":0,"pos1":[772,482],"pos2":[674,652],"color":"black"},{"type":"line","id":1,"pos1":[674,652],"pos2":[776,842],"color":"black"},{"type":"line","id":2,"pos1":[777,843],"pos2":[1019,861],"color":"black"},{"type":"line","id":3,"pos1":[1019,861],"pos2":[1159,735],"color":"black"},{"type":"line","id":4,"pos1":[1159,734],"pos2":[1079,538],"color":"black"},{"type":"line","id":5,"pos1":[781,496],"pos2":[847,571],"color":"black"},{"type":"line","id":6,"pos1":[847,571],"pos2":[939,584],"color":"black"},{"type":"line","id":7,"pos1":[939,584],"pos2":[1084,547],"color":"black"},{"type":"line","id":8,"pos1":[1084,547],"pos2":[1034,465],"color":"black"},{"type":"line","id":9,"pos1":[1034,465],"pos2":[876,441],"color":"black"},{"type":"line","id":10,"pos1":[876,441],"pos2":[784,497],"color":"black"},{"type":"line","id":11,"pos1":[844,530],"pos2":[720,161],"color":"black"},{"type":"line","id":12,"pos1":[922,503],"pos2":[962,255],"color":"black"},{"type":"line","id":13,"pos1":[1027,522],"pos2":[1221,248],"color":"black"},{"type":"line","id":14,"pos1":[729,176],"pos2":[645,95],"color":"black"},{"type":"line","id":15,"pos1":[645,95],"pos2":[728,59],"color":"black"},{"type":"line","id":16,"pos1":[728,59],"pos2":[787,152],"color":"black"},{"type":"line","id":17,"pos1":[795,163],"pos2":[737,178],"color":"black"},{"type":"line","id":18,"pos1":[972,266],"pos2":[893,185],"color":"black"},{"type":"line","id":19,"pos1":[893,185],"pos2":[1075,66],"color":"black"},{"type":"line","id":20,"pos1":[1075,66],"pos2":[1144,201],"color":"black"},{"type":"line","id":21,"pos1":[1144,201],"pos2":[973,265],"color":"black"},{"type":"line","id":22,"pos1":[1220,269],"pos2":[1149,47],"color":"black"},{"type":"line","id":23,"pos1":[1153,49],"pos2":[1469,137],"color":"black"},{"type":"line","id":24,"pos1":[1469,137],"pos2":[1216,267],"color":"black"}]'
) -- 2
;


SELECT * FROM sketches