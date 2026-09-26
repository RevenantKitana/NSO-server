#!/usr/bin/env bash
set -e

mkdir -p /home/ubuntu/.ssh
chmod 700 /home/ubuntu/.ssh

# Generate ed25519 deploy key if not exists
if [ ! -f /home/ubuntu/.ssh/github_deploy_key ]; then
    ssh-keygen -t ed25519 -C "vm-nso-deploy-key" -f /home/ubuntu/.ssh/github_deploy_key -N ""
fi

ssh-keyscan -t ed25519,rsa github.com >> /home/ubuntu/.ssh/known_hosts 2>/dev/null
sort -u /home/ubuntu/.ssh/known_hosts -o /home/ubuntu/.ssh/known_hosts
chmod 600 /home/ubuntu/.ssh/known_hosts
chmod 600 /home/ubuntu/.ssh/github_deploy_key
chmod 644 /home/ubuntu/.ssh/github_deploy_key.pub

cat <<'EOF' > /home/ubuntu/.ssh/config
Host github.com
    HostName github.com
    User git
    IdentityFile /home/ubuntu/.ssh/github_deploy_key
    IdentitiesOnly yes
EOF
chmod 600 /home/ubuntu/.ssh/config

# Setup git repo in /home/ubuntu/src
mkdir -p /home/ubuntu/src
cd /home/ubuntu/src
if [ ! -d .git ]; then
    git init
    git remote add origin git@github.com:RevenantKitana/NSO-server.git
else
    git remote set-url origin git@github.com:RevenantKitana/NSO-server.git
fi

# Create convenient 1-command update script
cat <<'EOF' > /home/ubuntu/update.sh
#!/usr/bin/env bash
set -e

echo "=== 1. Pulling latest code from GitHub ==="
cd /home/ubuntu/src
git fetch origin main
git reset --hard origin/main

echo "=== 2. Building JAR with Maven ==="
mvn clean package -DskipTests

echo "=== 3. Updating JAR and Restarting Service ==="
cp -f target/Nso-jar-with-dependencies.jar /home/ubuntu/nso-server/Nso-jar-with-dependencies.jar
sudo systemctl restart nso-server.service

echo "=== 4. Checking status ==="
sleep 3
sudo systemctl status nso-server.service --no-pager
echo "Update completed successfully!"
EOF
chmod +x /home/ubuntu/update.sh

echo "=== PUBLIC DEPLOY KEY ==="
cat /home/ubuntu/.ssh/github_deploy_key.pub
